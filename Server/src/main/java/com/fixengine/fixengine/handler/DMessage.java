package com.fixengine.fixengine.handler;

import com.fixengine.fixengine.generator.FixMessageGenerator;
import com.fixengine.fixengine.entity.FixMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class DMessage implements FixHandler {

    @Override
    public String getMessageType() {
        return "D";
    }

    @Override
    public FixMessage handleMessage(FixMessage message, WebSocketSession session) {
        FixMessage response = new FixMessage();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm");
        int seqNum = Integer.valueOf(message.getField(34)) + 1;


        if (message.getField(35).equals("D")) {
            response.addField(8, "FIX.4.2");
            response.addField(9, "");
            response.addField(35, "D");
            response.addField(49, "AXFix");
            response.addField(56, message.getField(49));
            response.addField(52, LocalDateTime.now().format(formatter));
            response.addField(11, message.getField(11));
            response.addField(34, String.valueOf(seqNum));
            response.addField(53, message.getField(54));
            response.addField(55, message.getField(55));
            response.addField(38, message.getField(38));
            response.addField(40, message.getField(40));
            response.addField(44, message.getField(44));
            response.addField(58, message.getField(58));
            response.addField(128, message.getField(128));
            response.addField(10, FixMessageGenerator.calculateSeqNum(response));

            response.addField(9, response.updateBodyLength());

        }

        return response;
    }
}
