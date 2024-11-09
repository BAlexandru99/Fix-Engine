package com.fixengine.fixengine.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.fixengine.fixengine.entity.FixMessage;
import com.fixengine.fixengine.generator.FixMessageGenerator;

@Component
public class OMessage implements FixHandler {

    @Override
    public FixMessage handleMessage(FixMessage message, WebSocketSession session) {
       FixMessage heartBeatMessage = FixMessageGenerator.createBaseMessage(message);
       heartBeatMessage.addField(35, "0");
        heartBeatMessage.removeField(108);        
       return heartBeatMessage; 
    }

    @Override
    public String getMessageType() {
        return "0";
    }
    
}
