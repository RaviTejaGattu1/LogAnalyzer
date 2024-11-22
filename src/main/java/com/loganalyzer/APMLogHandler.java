package com.loganalyzer;
import java.util.Map;

public class APMLogHandler extends LogHandler {
    public APMLogHandler(LogAggregator aggregator) {
        super(aggregator);
    }

    @Override
    protected boolean canHandle(LogEntry entry) {
        return entry.getAttribute("metric") != null && entry.getAttribute("value") != null;
    }

    @Override
    protected void process(LogEntry entry) {
        aggregator.aggregate(entry);
    }
}
