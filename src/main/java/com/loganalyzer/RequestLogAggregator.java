package com.loganalyzer;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RequestLogAggregator implements LogAggregator {
    private final Map<String, List<RequestMetric>> urlMetrics = new HashMap<>();

    private static class RequestMetric {
        final int responseTime;
        final int statusCode;

        RequestMetric(int responseTime, int statusCode) {
            this.responseTime = responseTime;
            this.statusCode = statusCode;
        }
    }

    @Override
    public void aggregate(LogEntry entry) {
        String url = entry.getAttribute("request_url");
        String responseTimeStr = entry.getAttribute("response_time_ms");
        String statusCodeStr = entry.getAttribute("response_status");

        if (url != null && responseTimeStr != null && statusCodeStr != null) {
            try {
                int responseTime = Integer.parseInt(responseTimeStr);
                int statusCode = Integer.parseInt(statusCodeStr);
                urlMetrics.computeIfAbsent(url, k -> new ArrayList<>())
                         .add(new RequestMetric(responseTime, statusCode));
            } catch (NumberFormatException ignored) {}
        }
    }

    @Override
    public Map<String, Object> summarize() {
        Map<String, Object> summary = new HashMap<>();
        
        for (Map.Entry<String, List<RequestMetric>> entry : urlMetrics.entrySet()) {
            Map<String, Object> urlStats = new HashMap<>();
            List<RequestMetric> metrics = entry.getValue();
            
            urlStats.put("response_times", calculateResponseTimeStats(metrics));
            urlStats.put("status_codes", calculateStatusCodeStats(metrics));
            
            summary.put(entry.getKey(), urlStats);
        }
        
        return summary;
    }

    private Map<String, Integer> calculateResponseTimeStats(List<RequestMetric> metrics) {
        List<Integer> responseTimes = metrics.stream()
            .map(m -> m.responseTime)
            .sorted()
            .collect(Collectors.toList());

        Map<String, Integer> stats = new HashMap<>();
        stats.put("min", responseTimes.get(0));
        stats.put("max", responseTimes.get(responseTimes.size() - 1));
        stats.put("50_percentile", calculatePercentile(responseTimes, 50));
        stats.put("90_percentile", calculatePercentile(responseTimes, 90));
        stats.put("95_percentile", calculatePercentile(responseTimes, 95));
        stats.put("99_percentile", calculatePercentile(responseTimes, 99));
        
        return stats;
    }

    private int calculatePercentile(List<Integer> sortedValues, int percentile) {
        int index = (int) Math.ceil(percentile / 100.0 * sortedValues.size()) - 1;
        return sortedValues.get(Math.min(index, sortedValues.size() - 1));
    }

    private Map<String, Integer> calculateStatusCodeStats(List<RequestMetric> metrics) {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("2XX", 0);
        stats.put("4XX", 0);
        stats.put("5XX", 0);

        for (RequestMetric metric : metrics) {
            String category = getStatusCodeCategory(metric.statusCode);
            stats.merge(category, 1, Integer::sum);
        }

        return stats;
    }

    private String getStatusCodeCategory(int statusCode) {
        if (statusCode >= 500) return "5XX";
        if (statusCode >= 400) return "4XX";
        return "2XX";
    }
}
