/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.runtime.broadcast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.flink.api.common.functions.BroadcastVariableInitializer;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.common.typeutils.TypeSerializerFactory;
import org.apache.flink.hadoop.shaded.org.jboss.netty.util.internal.ConcurrentHashMap;
import org.apache.flink.runtime.io.network.api.reader.AbstractReader;
import org.apache.flink.runtime.io.network.api.reader.MutableReader;
import org.apache.flink.runtime.io.network.partition.consumer.SingleInputGate;
import org.apache.flink.runtime.operators.BatchTask;
import org.apache.flink.runtime.operators.util.ReaderIterator;
import org.apache.flink.runtime.plugable.DeserializationDelegate;
import org.apache.flink.runtime.taskmanager.RuntimeEnvironment;
import org.apache.flink.runtime.taskmanager.Task;
import org.apache.flink.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @param <T> The type of the elements in the broadcasted data set.
 */
public class BroadcastVariableMaterialization<T, C> {

	private static final Logger LOG = LoggerFactory.getLogger(BroadcastVariableMaterialization.class);


	private final Set<BatchTask<?, ?>> references = new HashSet<BatchTask<?,?>>();

	private final Object materializationMonitor = new Object();

	private final Object materializationMonitorDE = new Object();

	private final BroadcastVariableKey key;

	private ArrayList<T> data;

	private C transformed;

	private boolean materialized;

	public boolean disposed;

	private MutableReader<?> reader;

	private ConcurrentHashMap<Integer,Integer> currentNumberOfReleaseAttempts;

	private int superstep;

	private AtomicInteger c;



	public BroadcastVariableMaterialization(BroadcastVariableKey key) {

		this.key = key;
		this.currentNumberOfReleaseAttempts = new ConcurrentHashMap<>();
		c= new AtomicInteger(0);
	}

	// --------------------------------------------------------------------------------------------

	public void materializeVariable(MutableReader<?> reader, TypeSerializerFactory<?> serializerFactory, BatchTask<?, ?> referenceHolder)
		throws MaterializationExpiredException, IOException
	{
		materializeVariable(reader,serializerFactory, referenceHolder, 0);
	}

	public synchronized void materializeVariable(MutableReader<?> reader, TypeSerializerFactory<?> serializerFactory, BatchTask<?, ?> referenceHolder, int superstep)
		throws MaterializationExpiredException, IOException
	{
		Preconditions.checkNotNull(reader);
		Preconditions.checkNotNull(serializerFactory);
		Preconditions.checkNotNull(referenceHolder);

		System.err.println("taskmanger: " + ((RuntimeEnvironment)referenceHolder.getEnvironment()).containingTask.taskManager.path() + "superstep: " + superstep + " start:" + ((SingleInputGate)((AbstractReader)reader).inputGate).consumedSubpartitionIndex);

		final boolean materializer;

		this.superstep = superstep;

		this.disposed = false;

		// hold the reference lock only while we track references and decide who should be the materializer
		// that way, other tasks can de-register (in case of failure) while materialization is happening
		synchronized (references) {
			if (disposed) {
				throw new MaterializationExpiredException();
			}

			// sanity check
			if (!references.add(referenceHolder)) {
				throw new IllegalStateException(
					String.format("The task %s already holds a reference to the broadcast variable %s.",
						referenceHolder.getEnvironment().getTaskInfo().getTaskNameWithSubtasks(),
						key.toString()));
			}

			materializer = references.size() == 1;
		}

		try {
			@SuppressWarnings("unchecked")
			final MutableReader<DeserializationDelegate<T>> typedReader = (MutableReader<DeserializationDelegate<T>>) reader;

			@SuppressWarnings("unchecked")
			final TypeSerializer<T> serializer = ((TypeSerializerFactory<T>) serializerFactory).getSerializer();

			//((SingleInputGate)((AbstractReader) reader).inputGate).requestedPartitionsFlag = false;
			final ReaderIterator<T> readerIterator = new ReaderIterator<T>(typedReader, serializer);

			if (materializer) {
				// first one, so we need to materialize;
				if (LOG.isDebugEnabled()) {
					LOG.debug("Getting Broadcast Variable (" + key + ") - First access, materializing.");
				}

				ArrayList<T> data = new ArrayList<T>();

				T element;
				while ((element = readerIterator.next()) != null) {
					data.add(element);
				}

				synchronized (materializationMonitor) {
					this.data = data;
					this.materialized = true;
					this.reader = reader;
					materializationMonitor.notifyAll();
					System.err.println("taskmanger: " + ((RuntimeEnvironment) referenceHolder.getEnvironment()).containingTask.taskManager.path() + " superstep: " + superstep + " Write Reader:" + ((SingleInputGate)((AbstractReader)reader).inputGate).consumedSubpartitionIndex + " data size: " + data.size() + " record: " + data.get(0));
				}

				if (LOG.isDebugEnabled()) {
					LOG.debug("Materialization of Broadcast Variable (" + key + ") finished.");
				}
			}
			else {
				// successor: discard all data and refer to the shared variable

				if (LOG.isDebugEnabled()) {
					LOG.debug("Getting Broadcast Variable (" + key + ") - shared access.");
				}
				/*
				T element = serializer.createInstance();
				while ((element = readerIterator.next(element)) != null);*/

				System.err.println("taskmanger: " + ((RuntimeEnvironment) referenceHolder.getEnvironment()).containingTask.taskManager.path() + " superstep: " + superstep + " Waiting:" + ((SingleInputGate)((AbstractReader)reader).inputGate).consumedSubpartitionIndex);

				synchronized (materializationMonitor) {
					while (!this.materialized && !disposed) {
						materializationMonitor.wait();
					}
				}

				System.err.println("taskmanger: " + ((RuntimeEnvironment) referenceHolder.getEnvironment()).containingTask.taskManager.path() + " superstep: " + superstep + " Done Waiting:" + ((SingleInputGate)((AbstractReader)reader).inputGate).consumedSubpartitionIndex);

			}
		}
		catch (Throwable t) {
			// in case of an exception, we need to clean up big time
			decrementReferenceIfHeld(referenceHolder);

			if (t instanceof IOException) {
				throw (IOException) t;
			} else {
				throw new IOException("Materialization of the broadcast variable failed.", t);
			}
		}
	}

	public MutableReader<?> getReader() {
		return reader;
	}

	public boolean decrementReference(BatchTask<?, ?> referenceHolder) {
		/*
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.err.println("reference:" + referenceHolder + "superstep: " + superstep + " decrement: " + c.getAndIncrement());
		/*
		Integer i = currentNumberOfReleaseAttempts.get(referenceHolder.);
		if (i == null) {
			i = 0;
		}
		i++;
		currentNumberOfReleaseAttempts.put(,i);
		if (i >= references.size()) {
			synchronized (materializationMonitorDE) {
				materializationMonitorDE.notifyAll();
				System.err.println("notify");
			}
		}
		
		System.err.println("releasing: " + currentNumberOfReleaseAttempts + " references:" + references.size() + " superstep: " + superstep);
		synchronized (materializationMonitorDE) {
			while (currentNumberOfReleaseAttempts.get(referenceHolder) >= references.size() && !disposed) {
				try {
					materializationMonitorDE.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.err.println("released: " + currentNumberOfReleaseAttempts);
		return decrementReferenceInternal(referenceHolder, true);*/
		return true;
	}

	public synchronized boolean decrementReference1(BatchTask<?, ?> referenceHolder, int superstep1) {
		//System.err.println("reference:" + referenceHolder + "superstep: " + superstep + " decrement: " + c.getAndIncrement());

		synchronized (materializationMonitorDE) {
			Integer i = currentNumberOfReleaseAttempts.get(superstep1);
			if (i == null) {
				i = 0;
			}
			i++;
			currentNumberOfReleaseAttempts.put(superstep1, i);
		}
		if (currentNumberOfReleaseAttempts.get(superstep1) >= references.size()) {
			synchronized (materializationMonitorDE) {
				materializationMonitorDE.notifyAll();
				System.err.println("taskmanger: " + ((RuntimeEnvironment)referenceHolder.getEnvironment()).containingTask.taskManager.path() +" released: " + currentNumberOfReleaseAttempts);
			}
		} else {
			System.err.println("taskmanger: " + ((RuntimeEnvironment) referenceHolder.getEnvironment()).containingTask.taskManager.path() + " releasing: " + currentNumberOfReleaseAttempts + " references:" + references.size() + " superstep: " + superstep1);

			synchronized (materializationMonitorDE) {
				while (currentNumberOfReleaseAttempts.get(superstep1) >= references.size() && !disposed) {
					try {
						System.err.println("taskmanger: " + ((RuntimeEnvironment) referenceHolder.getEnvironment()).containingTask.taskManager.path() + " release waiting: ");
						materializationMonitorDE.wait();

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			System.err.println("taskmanger: " + ((RuntimeEnvironment) referenceHolder.getEnvironment()).containingTask.taskManager.path() + " release done waiting: ");
		}
		return decrementReferenceInternal(referenceHolder, true);
		//return true;
	}

	public boolean decrementReferenceIfHeld(BatchTask<?, ?> referenceHolder) {
		return decrementReferenceInternal(referenceHolder, false);
	}

	private boolean decrementReferenceInternal(BatchTask<?, ?> referenceHolder, boolean errorIfNoReference) {
		synchronized (references) {
			if (disposed || references.isEmpty()) {
				if (errorIfNoReference) {
					throw new IllegalStateException("Decrementing reference to broadcast variable that is no longer alive.");
				} else {
					return false;
				}
			}

			if (!references.remove(referenceHolder)) {
				if (errorIfNoReference) {
					throw new IllegalStateException(
						String.format("The task %s did not hold a reference to the broadcast variable %s.",
							referenceHolder.getEnvironment().getTaskInfo().getTaskNameWithSubtasks(),
							key.toString()));
				} else {
					return false;
				}
			}


			if (references.isEmpty()) {
				disposed = true;
				data = null;
				transformed = null;
				return true;
			} else {
				return false;
			}
		}
	}

	// --------------------------------------------------------------------------------------------

	public List<T> getVariable() throws InitializationTypeConflictException {
		if (!materialized) {
			throw new IllegalStateException("The Broadcast Variable has not yet been materialized.");
		}
		if (disposed) {
			throw new IllegalStateException("The Broadcast Variable has been disposed");
		}

		synchronized (references) {
			if (transformed != null) {
				if (transformed instanceof List) {
					@SuppressWarnings("unchecked")
					List<T> casted = (List<T>) transformed;
					return casted;
				} else {
					throw new InitializationTypeConflictException(transformed.getClass());
				}
			}
			else {
				return data;
			}
		}
	}

	public C getVariable(BroadcastVariableInitializer<T, C> initializer) {
		if (!materialized) {
			throw new IllegalStateException("The Broadcast Variable has not yet been materialized.");
		}
		if (disposed) {
			throw new IllegalStateException("The Broadcast Variable has been disposed");
		}

		synchronized (references) {
			if (transformed == null) {
				transformed = initializer.initializeBroadcastVariable(data);
				data = null;
			}
			return transformed;
		}
	}
}
