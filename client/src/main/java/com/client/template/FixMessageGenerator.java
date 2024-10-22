package com.client.template;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import com.client.model.FixMessage;

public class FixMessageGenerator {
    private int seqNum = 1;

    public FixMessage generateMessage() {
        FixMessage message = new FixMessage();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm");

        message.addField(8, "FIX.4.2");
        message.addField(9, ""); 
        message.addField(35, ""); 
        message.addField(49, ""); 
        message.addField(56, "AXFix");
        message.addField(34, String.valueOf(seqNum++)); 
        message.addField(52, LocalDateTime.now().format(formatter)); 
        message.addField(108, "30"); 
        message.addField(10, generateCheckSum(message)); 

        return message;
    }

    

    private String generateCheckSum(FixMessage message) {
        StringBuilder fullMessage = new StringBuilder();
        for (Map.Entry<Integer, String> entry : message.getMessage().entrySet()) {
            fullMessage.append(entry.getKey()).append("=").append(entry.getValue()).append("\u0001");
        }

        int sum = 0;
        for (char c : fullMessage.toString().toCharArray()) {
            sum += (int) c;
        }

        return String.format("%03d", sum % 256);
    }
    public int calculateBodyLength(FixMessage message) {
        StringBuilder body = new StringBuilder();
        for (Map.Entry<Integer, String> entry : message.getMessage().entrySet()) {
            int tag = entry.getKey();
            if (tag != 8 && tag != 9 && tag != 10) {  // Exclude tag-urile BeginString, BodyLength, CheckSum
                body.append(tag).append("=").append(entry.getValue()).append("\u0001");
            }
        }
        return body.length(); 
    }
}
