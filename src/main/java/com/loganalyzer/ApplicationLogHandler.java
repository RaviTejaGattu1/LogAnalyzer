package com.loganalyzer;
import java.util.*;

public class ApplicationLogHandler extends LogHandler {
    public ApplicationLogHandler(LogAggregator aggregator) {
        super(aggregator);
    }

    @Override
    protected boolean canHandle(LogEntry entry) {
        return entry.getAttribute("level") != null && entry.getAttribute("message") != null;
    }

    @Override
    protected void process(LogEntry entry) {
        aggregator.aggregate(entry);
    }
}