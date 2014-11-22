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

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.hadoopcompatibility.mapreduce.FlinkParquetOutputFormat;
import parquet.hadoop.metadata.CompressionCodecName;
import parquet.hadoop.thrift.ParquetThriftOutputFormat;
import org.apache.flink.hadoopcompatibility.mapreduce.example.thrift.AminoAcid;
import org.apache.flink.hadoopcompatibility.mapreduce.example.thrift.AminoAcidType;

/**
 * Implements a word count which takes the input file and counts the number of
 * occurrences of each word in the file and writes the result back to disk.
 *
 * This example shows how to use Hadoop Input Formats, how to convert Hadoop Writables to 
 * common Java types for better usage in a Flink job and how to use Hadoop Output Formats.
 */
@SuppressWarnings("serial")
public class ParquetOutput {

    public static void main(String[] args) throws Exception {

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setDegreeOfParallelism(1);

        // Set up the Hadoop Input Format
        Job job = Job.getInstance();

        AminoAcid aa = new AminoAcid();
        aa.setFullName("Felix");
        aa.setMolecularWeight(75.3);
        aa.setType(AminoAcidType.AROMATIC);
        aa.setAbbreviation("FN");



        LongWritable lw = new LongWritable(1L);
        DataSet<Tuple2<LongWritable,AminoAcid>> data = env.fromElements(new Tuple2<LongWritable,AminoAcid>(lw, aa));


        //DataSet<Tuple2<Void,AminoAcid>> data = env.fromElements(new Tuple2<Void,AminoAcid>(null, aa));


        // Set up Hadoop Output Format
        FlinkParquetOutputFormat hadoopOutputFormat = new FlinkParquetOutputFormat(new ParquetThriftOutputFormat(), job);

        hadoopOutputFormat.getConfiguration().set("mapreduce.output.textoutputformat.separator", " ");
        hadoopOutputFormat.getConfiguration().set("mapred.textoutputformat.separator", " ");

        ParquetThriftOutputFormat.setOutputPath(job, new Path("newpath"));
        ParquetThriftOutputFormat.setThriftClass(job, AminoAcid.class);
        ParquetThriftOutputFormat.setCompression(job, CompressionCodecName.UNCOMPRESSED);
        ParquetThriftOutputFormat.setCompressOutput(job, false);
        ParquetThriftOutputFormat.setEnableDictionary(job, false);


        // Output & Execute
        data.output(hadoopOutputFormat);
        env.execute("Word Count");
    }




}