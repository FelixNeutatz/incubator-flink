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


import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.hadoopcompatibility.mapreduce.HadoopInputFormat;
import org.apache.flink.hadoopcompatibility.mapreduce.example.proto.ParsedPageProtos;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import parquet.proto.ProtoParquetInputFormat;

/**
 * Implements a word count which takes the input file and counts the number of
 * occurrences of each word in the file and writes the result back to disk.
 *
 * This example shows how to use Hadoop Input Formats, how to convert Hadoop Writables to 
 * common Java types for better usage in a Flink job and how to use Hadoop Output Formats.
 */
@SuppressWarnings("serial")
public class ParquetProtobufInput2 {

    public static void main(String[] args) throws Exception {

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setDegreeOfParallelism(1);

        Job job = Job.getInstance();

        HadoopInputFormat hadoopInputFormat = new HadoopInputFormat(new ProtoParquetInputFormat(), Void.class, ParsedPageProtos.ParsedPage.Builder.class, job);

        ProtoParquetInputFormat.addInputPath(job, new Path("newpath"));
        
        
        String projection = "message ParsedPage {required binary url;required int64 archiveTime; repeated binary scripts;}";
        ProtoParquetInputFormat.setRequestedProjection(job, projection);

        ProtoParquetInputFormat.setUnboundRecordFilter(job, ParquetProtobufFilter2.class);

        DataSet<Tuple2<Void, ParsedPageProtos.ParsedPage.Builder>> data = env.createInput(hadoopInputFormat);

        data.map(new TupleToProto()).print();


        env.execute("Word Count");
        
    }


    public static final class TupleToProto implements MapFunction<Tuple2<Void, ParsedPageProtos.ParsedPage.Builder>, ParsedPageProtos.ParsedPage> {

        @Override
        public ParsedPageProtos.ParsedPage map(Tuple2<Void, ParsedPageProtos.ParsedPage.Builder> value) {
            return value.f1.build();

        }
    }
}
