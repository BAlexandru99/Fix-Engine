package com.fixengine.fixengine.generator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fixengine.fixengine.entity.FixMessage;

public class FixMessageGenerator {

    public static FixMessage createBaseMessage(FixMessage message) {
        FixMessage response = new FixMessage();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm");

        response.addField(8, "FIX.4.2");
        response.addField(9, response.updateBodyLength());
        response.addField(35, "");
        response.addField(49, message.getField(56));
        response.addField(56, message.getField(49));
        response.addField(34, calculateSeqNum(message));
        response.addField(52, String.valueOf(LocalDateTime.now().format(formatter)));
        response.addField(108, "");
        response.addField(10, generateCheckSum(message));
        return response;
    }

    public static String calculateSeqNum(FixMessage message) {
        int messageValue = Integer.valueOf(message.getField(34));
        int responseValue = messageValue + 1;
        return String.valueOf(responseValue);
    }

    public static String generateCheckSum(FixMessage message) {
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

    public static void startHeartbeat(WebSocketSession session, int heartBtInt, FixMessage message) {
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(heartBtInt * 1000); // Wait for the specified HeartBtInt time

                    FixMessage heartbeat = createBaseMessage(message);
                    heartbeat.addField(35, "0");
                    heartbeat.removeField(108);
                    String heartbeatResponse = heartbeat.buildFixMessage();
                    session.sendMessage(new TextMessage(heartbeatResponse));
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
