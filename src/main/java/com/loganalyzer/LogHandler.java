package com.loganalyzer;
import java.util.Map;

public abstract class LogHandler {
    protected LogHandler nextHandler;
    protected LogAggregator aggregator;

    public LogHandler(LogAggregator aggregator) {
        this.aggregator = aggregator;
    }

    public void setNext(LogHandler handler) {
        this.nextHandler = handler;
    }

    public void handle(LogEntry entry) {
        if (canHandle(entry)) {
            process(entry);
        } else if (nextHandler != null) {
            nextHandler.handle(entry);
        }
    }

    protected abstract boolean canHandle(LogEntry entry);
    protected abstract void process(LogEntry entry);
}
