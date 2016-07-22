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

package org.apache.flink.examples.scala.ml

import org.apache.flink.api.common.functions._
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala._
import org.apache.flink.configuration.{ConfigConstants, Configuration}
import org.apache.flink.examples.java.ml.util.LinearRegressionData

import scala.collection.JavaConverters._

object LinearRegression {

  def main(args: Array[String]) {

    val params: ParameterTool = ParameterTool.fromArgs(args)

    val conf = new Configuration()
    conf.setInteger(ConfigConstants.LOCAL_NUMBER_TASK_MANAGER, 5)
    conf.setInteger(ConfigConstants.TASK_MANAGER_NUM_TASK_SLOTS, 2)
    conf.setString(ConfigConstants.AKKA_ASK_TIMEOUT, "2 h")

    val paralel = 4

    val env = ExecutionEnvironment.createLocalEnvironment(conf)
    env.setParallelism(6)
    // make parameters available in the web interface
    //env.getConfig.setGlobalJobParameters(params)

    val parameters = env.fromCollection(LinearRegressionData.PARAMS map {
      case Array(x, y) => Params(x.asInstanceOf[Double], y.asInstanceOf[Double])
    }).setParallelism(paralel)

    val data =
      if (params.has("input")) {
        env.readCsvFile[(Double, Double)](
          params.get("input"),
          fieldDelimiter = " ",
          includedFields = Array(0, 1))
          .map { t => new Data(t._1, t._2) }
      } else {
        println("Executing LinearRegression example with default input data set.")
        println("Use --input to specify file input.")
        val data = LinearRegressionData.DATA map {
          case Array(x, y) => Data(x.asInstanceOf[Double], y.asInstanceOf[Double])
        }
        env.fromCollection(data)
      }

    val numIterations = params.getInt("iterations", 10)

    val result = parameters.iterate(numIterations) { currentParameters =>
      val newParameters = data
        .map(new SubUpdate).withBroadcastSet(currentParameters, "parameters")
        .setParallelism(paralel)
        .reduce { (p1, p2) =>
          val result = p1._1 + p2._1
          (result, p1._2 + p2._2)
        }
        .map { x => x._1.div(x._2) }
      newParameters
    }

    if (params.has("output")) {
      result.writeAsText(params.get("output"))
      env.execute("Scala Linear Regression example")
    } else {
      println("Printing result to stdout. Use --output to specify output path.")
      result.print()
    }
  }

  /**
    * A simple data sample, x means the input, and y means the target.
    */
  case class Data(var x: Double, var y: Double)

  /**
    * A set of parameters -- theta0, theta1.
    */
  case class Params(theta0: Double, theta1: Double) {
    def div(a: Int): Params = {
      Params(theta0 / a, theta1 / a)
    }

    def + (other: Params) = {
      Params(theta0 + other.theta0, theta1 + other.theta1)
    }
  }

  // *************************************************************************
  //     USER FUNCTIONS
  // *************************************************************************

  /**
    * Compute a single BGD type update for every parameters.
    */
  class SubUpdate extends RichMapFunction[Data, (Params, Int)] {

    private var parameter: Params = null

    /** Reads the parameters from a broadcast variable into a collection. */
    override def open(parameters: Configuration) {
      val parameters = getRuntimeContext.getBroadcastVariable[Params]("parameters").asScala
      parameter = parameters.head
    }

    def map(in: Data): (Params, Int) = {
      val theta0 =
        parameter.theta0 - 0.01 * ((parameter.theta0 + (parameter.theta1 * in.x)) - in.y)
      val theta1 =
        parameter.theta1 - 0.01 * (((parameter.theta0 + (parameter.theta1 * in.x)) - in.y) * in.x)
      (Params(theta0, theta1), 1)
    }
  }
}
