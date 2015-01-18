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

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.TupleTypeInfo;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.hadoopcompatibility.mapreduce.HadoopOutputFormat;
import org.apache.flink.hadoopcompatibility.mapreduce.example.thrift.ParsedPage;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import parquet.hadoop.metadata.CompressionCodecName;
import parquet.hadoop.thrift.ParquetThriftOutputFormat;

import java.util.Arrays;
import java.util.List;

/**
 * Implements a word count which takes the input file and counts the number of
 * occurrences of each word in the file and writes the result back to disk.
 *
 * This example shows how to use Hadoop Input Formats, how to convert Hadoop Writables to 
 * common Java types for better usage in a Flink job and how to use Hadoop Output Formats.
 */
@SuppressWarnings("serial")
public class ParquetThriftOutput2 {

    public static void main(String[] args) throws Exception {

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setDegreeOfParallelism(1);

        // Set up the Hadoop Input Format
        Job job = Job.getInstance();

        ParsedPage aa = new ParsedPage();

        aa.setUrl("www.test.de").setArchiveTime(123L);

        aa.addToScripts("script.de");
        aa.addToScripts("script1.de");
        aa.addToIframes("iframe.de");
        aa.addToIframes("iframe1.de");
        aa.addToLinks("link.de");
        aa.addToLinks("link1.de");
        aa.addToImages("img.de");
        aa.addToImages("img1.de");


        List l = Arrays.asList(new Tuple2<Void,ParsedPage>(null, aa));
        TypeInformation t = new TupleTypeInfo<Tuple2<Void,ParsedPage>>(TypeExtractor.getForClass(Void.class), TypeExtractor.getForClass(ParsedPage.class));

        DataSet<Tuple2<Void,ParsedPage>> data = env.fromCollection(l,t);


        // Set up Hadoop Output Format
        HadoopOutputFormat hadoopOutputFormat = new HadoopOutputFormat(new ParquetThriftOutputFormat(), job);

        ParquetThriftOutputFormat.setOutputPath(job, new Path("newpath"));
        ParquetThriftOutputFormat.setThriftClass(job, ParsedPage.class);
        ParquetThriftOutputFormat.setCompression(job, CompressionCodecName.SNAPPY);
        ParquetThriftOutputFormat.setCompressOutput(job, true);
        ParquetThriftOutputFormat.setEnableDictionary(job, true);


        // Output & Execute
        data.output(hadoopOutputFormat);

        env.execute("Word Count");
    }




}
