package com.fixengine.fixengine.entity;

import java.util.*;

public class FixMessage {
    private Map<Integer , String> fields = new HashMap<>();

    public void addField(int tag, String value) {
        fields.put(tag, value);
    }

    public String getField(int tag) {
        return fields.get(tag);
    }

    public String buildFixMessage(){
        StringBuilder message = new StringBuilder();
        for(Map.Entry<Integer , String> entry : fields.entrySet()){
            message.append(entry.getKey() + " = " + entry.getValue()).append("|");
        }
        return message.toString();
    }
    public static FixMessage parseFixMessage(String fixMessage) {
        FixMessage message = new FixMessage();
        String[] keyValuePairs = fixMessage.split("\\|");
        for(String pair : keyValuePairs){
            String[] keyValue = pair.split("=");
            if(keyValue.length == 2){
                int tag = Integer.parseInt(keyValue[0]);
                String value = keyValue[1];
                message.addField(tag, value);
            }
        }
        return message;
    }
}
