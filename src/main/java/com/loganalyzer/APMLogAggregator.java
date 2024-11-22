// import java.
package com.loganalyzer; 
import java.util.*;

public class APMLogAggregator implements LogAggregator {
    private final Map<String, List<Double>> metricValues = new HashMap<>();

    @Override
    public void aggregate(LogEntry entry) {
        String metric = entry.getAttribute("metric");
        String valueStr = entry.getAttribute("value");
        
        if (metric != null && valueStr != null) {
            try {
                double value = Double.parseDouble(valueStr);
                metricValues.computeIfAbsent(metric, k -> new ArrayList<>()).add(value);
            } catch (NumberFormatException ignored) {}
        }
    }

    @Override
    public Map<String, Object> summarize() {
        Map<String, Object> summary = new HashMap<>();
        
        for (Map.Entry<String, List<Double>> entry : metricValues.entrySet()) {
            List<Double> values = entry.getValue();
            if (!values.isEmpty()) {
                Map<String, Double> stats = calculateStats(values);
                summary.put(entry.getKey(), stats);
            }
        }
        
        return summary;
    }

    private Map<String, Double> calculateStats(List<Double> values) {
        Map<String, Double> stats = new HashMap<>();
        values.sort(Double::compareTo);
        
        stats.put("minimum", values.get(0));
        stats.put("maximum", values.get(values.size() - 1));
        stats.put("average", values.stream().mapToDouble(d -> d).average().orElse(0.0));
        stats.put("median", calculateMedian(values));
        
        return stats;
    }

    private double calculateMedian(List<Double> sortedValues) {
        int size = sortedValues.size();
        if (size % 2 == 0) {
            return (sortedValues.get(size/2 - 1) + sortedValues.get(size/2)) / 2.0;
        } else {
            return sortedValues.get(size/2);
        }
    }
}