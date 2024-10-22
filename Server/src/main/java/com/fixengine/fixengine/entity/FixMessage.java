package com.fixengine.fixengine.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class FixMessage {
    private Map<Integer, String> fields = new LinkedHashMap<>();

    public void addField(int tag, String value) {
        fields.put(tag, value);
    }

    public String getField(int tag) {
        return fields.get(tag);
    }

    public void removeField(int tag) {
        fields.remove(tag);
    }

    public String buildFixMessage() {
        String bodyLengthValue = updateBodyLength();
        fields.put(9, bodyLengthValue);

        StringBuilder message = new StringBuilder();

        for (Map.Entry<Integer, String> entry : fields.entrySet()) {
            message.append(entry.getKey()).append("=").append(entry.getValue()).append("|");
        }

        return message.toString();
    }

    public static FixMessage parseFixMessage(String fixMessage) {
        FixMessage message = new FixMessage();
        String[] keyValuePairs = fixMessage.split("\\|");
        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                int tag = Integer.parseInt(keyValue[0]);
                String value = keyValue[1];
                message.addField(tag, value);
            }
        }
        return message;
    }

    public String updateBodyLength() {
        StringBuilder messageWithoutBodyLength = new StringBuilder();
        
        for (Map.Entry<Integer, String> entry : fields.entrySet()) {
            if (entry.getKey() != 8 && entry.getKey() != 9 && entry.getKey() != 10) {
                messageWithoutBodyLength.append(entry.getKey()).append("=").append(entry.getValue()).append((char) 1); // SOH character
            }
        }
    
        int bodyLength = messageWithoutBodyLength.length();
        return String.valueOf(bodyLength);
    }

    public Set<Integer> getTags() {
        return fields.keySet();
    }
}
