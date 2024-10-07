package com.fixengine.fixengine.Sockets;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fixengine.fixengine.entity.FixMessage;
import com.fixengine.fixengine.generator.FixMessageGenerator;
import com.fixengine.fixengine.validator.FixMessageValidator;

import lombok.NoArgsConstructor;

import org.springframework.boot.autoconfigure.jms.JmsProperties.Listener.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

@Component
@NoArgsConstructor

public class MyWebSocketHandler extends TextWebSocketHandler {


    @Override
    public void handleTextMessage(WebSocketSession session , TextMessage message) throws Exception{
        
        String payload = message.getPayload();

        System.out.println(payload);

        FixMessage fixMessage = FixMessage.parseFixMessage(payload);

        if(FixMessageValidator.validateFixMessage(fixMessage)){
            FixMessage response = FixMessageGenerator.generateExecutionReport(fixMessage);
            session.sendMessage(new TextMessage(response.buildFixMessage()));
        }else{
            session.sendMessage(new TextMessage("Mesaj FIX invalid, lipsește un câmp obligatoriu."));
        }
    }
}
