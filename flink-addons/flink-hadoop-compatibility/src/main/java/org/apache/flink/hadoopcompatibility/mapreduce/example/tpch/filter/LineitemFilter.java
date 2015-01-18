package org.apache.flink.hadoopcompatibility.mapreduce.example.tpch.filter;


import parquet.column.ColumnReader;
import parquet.filter.ColumnPredicates;
import parquet.filter.ColumnRecordFilter;
import parquet.filter.RecordFilter;
import parquet.filter.UnboundRecordFilter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LineitemFilter implements UnboundRecordFilter {

    public class AfterDate implements ColumnPredicates.PredicateFunction<String> {

        @Override
        public boolean functionToApply(String input){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = format.parse("1995-03-12");
                return format.parse(input).after(date);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public RecordFilter bind(Iterable<ColumnReader> readers){       
        
        return ColumnRecordFilter.column(
                "SHIPDATE",
                ColumnPredicates.applyFunctionToString(new AfterDate())
        ).bind(readers);
    }
}
