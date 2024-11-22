package com.loganalyzer;
import java.util.Map;

public class RequestLogHandler extends LogHandler {
    public RequestLogHandler(LogAggregator aggregator) {
        super(aggregator);
    }

    @Override
    protected boolean canHandle(LogEntry entry) {
        return entry.getAttribute("request_url") != null && 
               entry.getAttribute("response_status") != null && 
               entry.getAttribute("response_time_ms") != null;
    }

    @Override
    protected void process(LogEntry entry) {
        aggregator.aggregate(entry);
    }
}
