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

package org.apache.flink.hadoopcompatibility.mapreduce.example;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.hadoopcompatibility.mapreduce.HadoopInputFormat;
import org.apache.flink.hadoopcompatibility.mapreduce.example.thrift.ParsedPage;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import parquet.hadoop.thrift.ParquetThriftInputFormat;

/**
 * Implements a word count which takes the input file and counts the number of
 * occurrences of each word in the file and writes the result back to disk.
 *
 * This example shows how to use Hadoop Input Formats, how to convert Hadoop Writables to 
 * common Java types for better usage in a Flink job and how to use Hadoop Output Formats.
 */
@SuppressWarnings("serial")
public class ParquetThriftInput2 {

    public static void main(String[] args) throws Exception {

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setDegreeOfParallelism(1);

        Job job = Job.getInstance();

        HadoopInputFormat hadoopInputFormat = new HadoopInputFormat(new ParquetThriftInputFormat(), Void.class, ParsedPage.class, job);

        ParquetThriftInputFormat.addInputPath(job, new Path("newpath"));
        ParquetThriftInputFormat.setReadSupportClass(job, ParsedPage.class);


        DataSet<Tuple2<LongWritable, ParsedPage>> data = env.createInput(hadoopInputFormat);

        data.print();

        env.execute("Word Count");
    }




}
