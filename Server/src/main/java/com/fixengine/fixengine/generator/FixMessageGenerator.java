package com.fixengine.fixengine.generator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
}
