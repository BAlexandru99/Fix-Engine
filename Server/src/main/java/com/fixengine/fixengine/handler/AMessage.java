package com.fixengine.fixengine.handler;

import com.fixengine.fixengine.generator.FixMessageGenerator;
import com.fixengine.fixengine.entity.FixMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.stereotype.Component;

@Component
public class AMessage implements FixHandler {

    @Override
    public FixMessage handleMessage(FixMessage message, WebSocketSession session) {
       FixMessage response = FixMessageGenerator.createBaseMessage(message);
       if(message.getField(35).equals("A")){
            response.addField(35, "A");
            response.addField(108, message.getField(108));

            int heartBtInt = Integer.parseInt(message.getField(108));
            FixMessageGenerator.startHeartbeat(session, heartBtInt, message);
       }
       return response;
    }

    @Override
    public String getMessageType() {
        return "A";
    }
    
}
