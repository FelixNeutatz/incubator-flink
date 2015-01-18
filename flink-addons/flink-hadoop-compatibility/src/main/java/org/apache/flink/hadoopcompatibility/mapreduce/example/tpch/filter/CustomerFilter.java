package org.apache.flink.hadoopcompatibility.mapreduce.example.tpch.filter;


import parquet.column.ColumnReader;
import parquet.filter.ColumnPredicates;
import parquet.filter.ColumnRecordFilter;
import parquet.filter.RecordFilter;
import parquet.filter.UnboundRecordFilter;

public class CustomerFilter implements UnboundRecordFilter {

    public RecordFilter bind(Iterable<ColumnReader> readers){       
        
        return ColumnRecordFilter.column(
                "MKTSEGMENT",
                ColumnPredicates.equalTo("AUTOMOBILE")
        ).bind(readers);
    }
}
