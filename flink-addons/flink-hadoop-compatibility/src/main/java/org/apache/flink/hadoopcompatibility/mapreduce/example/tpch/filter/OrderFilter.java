package org.apache.flink.hadoopcompatibility.mapreduce.example.tpch.filter;


import parquet.column.ColumnReader;
import parquet.filter.ColumnPredicates;
import parquet.filter.ColumnRecordFilter;
import parquet.filter.RecordFilter;
import parquet.filter.UnboundRecordFilter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class OrderFilter implements UnboundRecordFilter {

    public class BeforeDate implements ColumnPredicates.PredicateFunction<String> {

        @Override
        public boolean functionToApply(String input){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = format.parse("1995-03-12");
                return format.parse(input).before(date);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public RecordFilter bind(Iterable<ColumnReader> readers){       
        
        return ColumnRecordFilter.column(
                "ORDERDATE",
                ColumnPredicates.applyFunctionToString(new BeforeDate())
        ).bind(readers);
    }
}
