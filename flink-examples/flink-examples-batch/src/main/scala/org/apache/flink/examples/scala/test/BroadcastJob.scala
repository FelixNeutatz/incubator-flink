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

package org.apache.flink.examples.scala.test

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala._
import org.apache.flink.configuration.{ConfigConstants, Configuration}
import org.apache.flink.core.fs.FileSystem.WriteMode

import scala.collection.convert.wrapAsScala._

/**
  * Flink job that distributes of DataSet of longs with 
  * a given size (in mb) to a set amount of map task and does some
  * arbitrary computation.
  */
object BroadcastJob {

  val BYTES_PER_LONG = 8
  val BYTES_PER_MB = 1024 * 1024

  def main(args: Array[String]) {

    val numMapTasks = 4
    val vectorSizeInMB = 25
    
    

    val numVectorElements = 10//vectorSizeInMB * BYTES_PER_MB / BYTES_PER_LONG

    val conf = new Configuration()
    conf.setInteger(ConfigConstants.LOCAL_NUMBER_TASK_MANAGER, 2)
    conf.setInteger(ConfigConstants.TASK_MANAGER_NUM_TASK_SLOTS, 4)
    conf.setString(ConfigConstants.AKKA_ASK_TIMEOUT, "2 h");
    
    val env = ExecutionEnvironment.createLocalEnvironment(conf)

    // generate a NumberSequence to map over
    // one number per task/dop
    val matrix: DataSet[Long] = env
      .fromParallelCollection(new NumberSequenceIteratorWrapper(1, numMapTasks))
      .setParallelism(numMapTasks)
      .name(s"Generate mapper dataset [1..$numMapTasks]")

    // generate the broadcast DataSet
    val vector: DataSet[Long] = env
      .fromParallelCollection(new NumberSequenceIteratorWrapper(1, numVectorElements))
      .setParallelism(1)
      .name(s"Generate broadcast vector (${vectorSizeInMB}mb)")

    val result: DataSet[Long] = matrix.map(new RichMapFunction[Long, Long] {
      var bcastVector: scala.collection.mutable.Buffer[Long] = null

      override def open(conf: Configuration) {
        bcastVector = getRuntimeContext.getBroadcastVariable[Long]("bVector")
      }

      override def map(value: Long): Long = {
        Math.max(bcastVector.last, value)
      }
    }).withBroadcastSet(vector, "bVector")

    result.writeAsText("/home/felix/broadcast/text.txt", WriteMode.OVERWRITE)
    env.execute(s"Broadcast Job - dop: $numMapTasks, broadcast vector (mb): $vectorSizeInMB")
  }
}
