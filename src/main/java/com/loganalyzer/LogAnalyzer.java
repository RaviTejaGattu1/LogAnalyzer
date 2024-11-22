// LogAnalyzer.java
package com.loganalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;


public class LogAnalyzer {
    private final Map<String, LogAggregator> aggregators;
    private final LogHandler firstHandler;

    public LogAnalyzer() {
        // Initialize aggregators
        this.aggregators = new HashMap<>();
        this.aggregators.put("apm", new APMLogAggregator());
        this.aggregators.put("application", new ApplicationLogAggregator());
        this.aggregators.put("request", new RequestLogAggregator());

        // Set up the chain of handlers
        LogHandler apmHandler = new APMLogHandler(aggregators.get("apm"));
        LogHandler appHandler = new ApplicationLogHandler(aggregators.get("application"));
        LogHandler reqHandler = new RequestLogHandler(aggregators.get("request"));

        apmHandler.setNext(appHandler);
        appHandler.setNext(reqHandler);
        
        this.firstHandler = apmHandler;
    }

    public void processLog(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogEntry entry = new LogEntry(line);
                firstHandler.handle(entry);
            }
        }
    }

    public void writeResults() throws IOException {
        writeJson("apm.json", aggregators.get("apm").summarize());
        writeJson("application.json", aggregators.get("application").summarize());
        writeJson("request.json", aggregators.get("request").summarize());
    }

    private void writeJson(String filename, Map<String, Object> data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), data);
    }

    public static void main(String[] args) {
        if (args.length != 2 || !args[0].equals("--file")) {
            System.err.println("Usage: java LogAnalyzer --file <filename.txt>");
            System.exit(1);
        }

        LogAnalyzer analyzer = new LogAnalyzer();
        try {
            analyzer.processLog(args[1]);
            analyzer.writeResults();
        } catch (IOException e) {
            System.err.println("Error processing log file: " + e.getMessage());
            System.exit(1);
        }
    }
}