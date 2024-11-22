package com.loganalyzer;
import java.util.*;


public class LogEntry {
    private final String rawContent;
    private final Map<String, String> attributes;

    public LogEntry(String rawContent) {
        this.rawContent = rawContent;
        this.attributes = parseAttributes(rawContent);
    }

    private Map<String, String> parseAttributes(String content) {
        Map<String, String> attrs = new HashMap<>();
        String[] pairs = content.split("\\s+");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                // Remove any surrounding quotes from the value
                String value = keyValue[1].replaceAll("^\"|\"$", "");
                attrs.put(keyValue[0], value);
            }
        }
        return attrs;
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    public String getRawContent() {
        return rawContent;
    }
}