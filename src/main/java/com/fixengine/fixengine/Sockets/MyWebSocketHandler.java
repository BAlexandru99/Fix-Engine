package com.fixengine.fixengine.Sockets;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fixengine.fixengine.entity.FixMessage;
import com.fixengine.fixengine.generator.FixMessageGenerator;
import com.fixengine.fixengine.handler.DMessage;
import com.fixengine.fixengine.handler.FixHandler;
import com.fixengine.fixengine.router.FixHandlerRouter;
import com.fixengine.fixengine.store.storeMessage;
import com.fixengine.fixengine.validator.FixMessageValidator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.jms.JmsProperties.Listener.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

@Component
@AllArgsConstructor


public class MyWebSocketHandler extends TextWebSocketHandler {;

    FixHandler fixHandler;
    storeMessage storeMessage;
    FixHandlerRouter handlerRouter;

    @Override
    public void handleTextMessage(WebSocketSession session , TextMessage message) throws Exception{
        
        String payload = message.getPayload();

        System.out.println(payload);
        FixMessage fixMessage = FixMessage.parseFixMessage(payload);

        if(FixMessageValidator.validateFixMessage(fixMessage)){
            FixMessage responseMessage =  handlerRouter.routeMessage(fixMessage);
            String response = responseMessage.buildFixMessage();

            session.sendMessage(new TextMessage(response));
            
            storeMessage.storeFixMessages(payload);
            storeMessage.storeFixMessages(response);
            
        }else{
            session.sendMessage(new TextMessage("FIX message invalid, a required field is missing."));
        }
    }
}
