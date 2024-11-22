package com.loganalyzer;
import java.util.Map;

public interface LogAggregator {
    void aggregate(LogEntry entry);
    Map<String, Object> summarize();
}