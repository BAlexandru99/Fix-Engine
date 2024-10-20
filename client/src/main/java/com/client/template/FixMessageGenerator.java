package com.client.template;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Map.Entry;

import com.client.model.FixMessage;

public class FixMessageGenerator {

    private int seqNum = 1;
    
    public FixMessage generateMessage(){
        FixMessage message = new FixMessage();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm");


        message.addField(8, "FIX.4.2");
        message.addField(9, "");
        message.addField(35, "");
        message.addField(49, "");
        message.addField(56, "AXFix");
        message.addField(34, String.valueOf(seqNum));
        message.addField(52, String.valueOf(LocalDateTime.now().format(formatter)));
        message.addField(108, "");
        message.addField(10, generateCheckSum(message));

        int bodyLenght = calculateBodyLenght(message);
        message.addField(9, String.valueOf(bodyLenght));

        seqNum++;

        return message;
    }

    private int calculateBodyLenght(FixMessage message){
        StringBuilder body = new StringBuilder();

        for (Map.Entry<Integer, String> entry : message.getMessage().entrySet()){
            if (entry.getKey() != 8 && entry.getKey() !=9 && entry.getKey() != 10) {
                body.append(entry.getKey()).append("=").append(entry.getValue()).append("\u0001");
            }
        }
        return body.length();
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
}
