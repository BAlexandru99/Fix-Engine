package com.fixengine.fixengine.Sockets;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fixengine.fixengine.entity.FixMessage;

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

        String messageType = fixMessage.getField(35);

        session.sendMessage(new TextMessage("Tip mesaj FIX primit: " + messageType));
    }
}
