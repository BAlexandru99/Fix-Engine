package com.client.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class FixMessage {
    private Map<Integer, String> fields = new LinkedHashMap<>();

    public void addField(int key, String value) {
        fields.put(key, value);
    }

    public String getValue(int key) {
        return fields.get(key);
    }

    public Map<Integer, String> getMessage() {
        return this.fields;
    }

    public String buildFixMessage() {
        StringBuilder message = new StringBuilder();
        for (Map.Entry<Integer, String> entry : fields.entrySet()) {
            message.append(entry.getKey()).append("=").append(entry.getValue()).append("|");
        }
        return message.toString();
    }
}
