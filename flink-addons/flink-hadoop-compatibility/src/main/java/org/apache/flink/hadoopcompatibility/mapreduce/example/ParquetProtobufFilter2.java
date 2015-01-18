package org.apache.flink.hadoopcompatibility.mapreduce.example;

import parquet.column.ColumnReader;
import parquet.filter.ColumnPredicates;
import parquet.filter.ColumnRecordFilter;
import parquet.filter.RecordFilter;
import parquet.filter.UnboundRecordFilter;

/**
 * Created by felix on 17.01.15.
 */
public class ParquetProtobufFilter2 implements UnboundRecordFilter {
    public RecordFilter bind(Iterable<ColumnReader> readers){
        return ColumnRecordFilter.column(
                "url",
                ColumnPredicates.equalTo("www.test.de")
        ).bind(readers);
    }
}
