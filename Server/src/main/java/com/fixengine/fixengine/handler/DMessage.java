package com.fixengine.fixengine.handler;

import com.fixengine.fixengine.generator.FixMessageGenerator;
import com.fixengine.fixengine.entity.FixMessage;

import java.net.http.WebSocket;
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
        FixMessage response = FixMessageGenerator.createBaseMessage(message);
        if (message.getField(35).equals("D")) {
            response.addField(35, "8");
            response.addField(39, "2");  
        }

        return response;
    }
    
}
