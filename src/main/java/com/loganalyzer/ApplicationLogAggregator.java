package com.loganalyzer;

import java.util.Map;
import java.util.*;



public class ApplicationLogAggregator implements LogAggregator {
    private final Map<String, Integer> levelCounts = new HashMap<>();

    @Override
    public void aggregate(LogEntry entry) {
        String level = entry.getAttribute("level");
        if (level != null) {
            levelCounts.merge(level, 1, Integer::sum);
        }
    }

    @Override
    public Map<String, Object> summarize() {
        return new HashMap<>(levelCounts);
    }
}