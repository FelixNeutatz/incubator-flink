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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.flink.api.common.typeutils.TypeSerializerFactory;
import org.apache.flink.runtime.io.network.api.reader.MutableReader;
import org.apache.flink.runtime.operators.BatchTask;

public class BroadcastVariableManager {
	
	private final ConcurrentHashMap<BroadcastVariableKey, BroadcastVariableMaterialization<?, ?>> variables =
							new ConcurrentHashMap<BroadcastVariableKey, BroadcastVariableMaterialization<?, ?>>(16);

	private MutableReader<?> reader = null;

	private final Object releaseMonitor = new Object();
	private final ConcurrentHashMap<Integer, Integer> queue =
		new ConcurrentHashMap<Integer, Integer>();


	// --------------------------------------------------------------------------------------------
	
	public <T> BroadcastVariableMaterialization<T, ?> materializeBroadcastVariable(String name, int superstep, BatchTask<?, ?> holder,
			MutableReader<?> reader, TypeSerializerFactory<T> serializerFactory) throws IOException
	{
		final BroadcastVariableKey key = new BroadcastVariableKey(holder.getEnvironment().getJobVertexId(), name, superstep);
		
		synchronized (releaseMonitor) {
			Integer count = queue.get(superstep);
			if (count == null) {
				count = 0;
			}
			count++;
			queue.put(superstep, count);
		}
		
		while (true) {
			final BroadcastVariableMaterialization<T, Object> newMat = new BroadcastVariableMaterialization<T, Object>(key);
			
			final BroadcastVariableMaterialization<?, ?> previous = variables.putIfAbsent(key, newMat);
			
			@SuppressWarnings("unchecked")
			final BroadcastVariableMaterialization<T, ?> materialization = (previous == null) ? newMat : (BroadcastVariableMaterialization<T, ?>) previous;
			
			try {
				if (this.reader == null) {
					MutableReader<?> r = materialization.materializeVariable(reader, serializerFactory, holder);
					if (r != null) {
						this.reader = r;
					}
				} else {
					materialization.materializeVariable(this.reader, serializerFactory, holder);
				}
				return materialization;
			}
			catch (MaterializationExpiredException e) {
				// concurrent release. as an optimization, try to replace the previous one with our version. otherwise we might spin for a while
				// until the releaser removes the variable
				// NOTE: This would also catch a bug prevented an expired materialization from ever being removed, so it acts as a future safeguard
				
				boolean replaceSuccessful = false;
				try {
					replaceSuccessful = variables.replace(key, materialization, newMat);
				}
				catch (Throwable t) {}
				
				if (replaceSuccessful) {
					try {
						if (this.reader == null) {
							MutableReader<?> r = newMat.materializeVariable(reader, serializerFactory, holder);
							if (r != null) {
								this.reader = r;
							}
						} else {
							newMat.materializeVariable(this.reader, serializerFactory, holder);
						}
						return newMat;
					}
					catch (MaterializationExpiredException ee) {
						// can still happen in cases of extreme races and fast tasks
						// fall through the loop;
					}
				}
				// else fall through the loop
			}
		}
	}
	
	public void releaseReference(String name, int superstep, BatchTask<?, ?> referenceHolder) {
		synchronized (releaseMonitor) {
			Integer count = queue.get(superstep);
			if (count != null) {
				queue.put(superstep, count - 1);
			} 
			if (count == 1) {
				releaseMonitor.notifyAll();
			}
		}

		synchronized (releaseMonitor) {
			while (queue.get(superstep) != 0) {
				try {
					releaseMonitor.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		BroadcastVariableKey key = new BroadcastVariableKey(referenceHolder.getEnvironment().getJobVertexId(), name, superstep);
		releaseReference(key, referenceHolder);
	}
	
	private void releaseReference(BroadcastVariableKey key, BatchTask<?, ?> referenceHolder) {
		BroadcastVariableMaterialization<?, ?> mat = variables.get(key);
		
		// release this reference
		if (mat.decrementReference(referenceHolder)) {
			// remove if no one holds a reference and no one concurrently replaced the entry
			variables.remove(key, mat);
		}
	}
	
	
	public void releaseAllReferencesFromTask(BatchTask<?, ?> referenceHolder) {
		// go through all registered variables 
		for (Map.Entry<BroadcastVariableKey, BroadcastVariableMaterialization<?, ?>> entry : variables.entrySet()) {
			BroadcastVariableMaterialization<?, ?> mat = entry.getValue();
			
			// release the reference
			if (mat.decrementReferenceIfHeld(referenceHolder)) {
				// remove if no one holds a reference and no one concurrently replaced the entry
				variables.remove(entry.getKey(), mat);
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
	
	public int getNumberOfVariablesWithReferences() {
		return this.variables.size();
	}
}
