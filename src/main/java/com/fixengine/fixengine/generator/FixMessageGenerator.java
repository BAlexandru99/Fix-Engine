package com.fixengine.fixengine.generator;

import java.io.IOException;
import java.net.http.WebSocket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fixengine.fixengine.entity.FixMessage;
import com.fixengine.fixengine.handler.DMessage;
import com.fixengine.fixengine.handler.FixHandler;


public class FixMessageGenerator {
    
    public static FixMessage createBaseMessage(FixMessage message){
        FixMessage respose = new FixMessage();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm");

        respose.addField(8, "FIX.4.2");
        respose.addField(9, respose.updateBodyLength());
        respose.addField(35, "");
        respose.addField(34, calculateSeqNum(message));
        respose.addField(49, message.getField(56));
        respose.addField(52, String.valueOf(LocalDateTime.now().format(formatter)));
        respose.addField(56, message.getField(49));
        respose.addField(10, generateCheckSum(message));
        return respose;
    }

    public static String calculateSeqNum(FixMessage message){
        int messageValue = Integer.valueOf(message.getField(34));
        int responseValue = messageValue + 1;
        return String.valueOf(responseValue);
    }

    public static String generateCheckSum(FixMessage message){
        StringBuilder sb = new StringBuilder();
        for (Integer tag : message.getTags()) {
            if (tag == 10) continue;
    
            String value = message.getField(tag);
            sb.append(tag).append("=").append(value).append((char) 1);
        }
    
        int sum = 0;
        for (int i = 0; i < sb.length(); i++) {
            sum += sb.charAt(i);
        }
    
        int checksumValue = sum % 256;
    
        return String.format("%03d", checksumValue);
    }

    public static void startHeartbeat(WebSocketSession session, int heartBtInt) {
    new Thread(() -> {
        try {
            while (true) {
                Thread.sleep(heartBtInt * 1000);  // Așteaptă timpul specificat de HeartBtInt
                
                FixMessage heartbeat = new FixMessage();
                heartbeat.addField(35, "0"); // Heartbeat
                heartbeat.addField(52, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss"))); // Setează timpul curent

                String heartbeatResponse = heartbeat.buildFixMessage(); 
                session.sendMessage(new TextMessage(heartbeatResponse));
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }).start();
    }
}
