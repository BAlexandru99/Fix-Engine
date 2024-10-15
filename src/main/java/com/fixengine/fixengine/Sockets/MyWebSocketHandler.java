package com.fixengine.fixengine.Sockets;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fixengine.fixengine.entity.FixMessage;
import com.fixengine.fixengine.router.FixHandlerRouter;
import com.fixengine.fixengine.store.storeMessage;
import com.fixengine.fixengine.validator.FixMessageValidator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

@Component
@AllArgsConstructor
public class MyWebSocketHandler extends TextWebSocketHandler {

    storeMessage storeMessage;
    FixHandlerRouter handlerRouter;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        
        String payload = message.getPayload();

        System.out.println(payload);
        FixMessage fixMessage = FixMessage.parseFixMessage(payload);

        if (FixMessageValidator.validateFixMessage(fixMessage)) {
            FixMessage responseMessage = handlerRouter.routeMessage(fixMessage, session);
            String response = responseMessage.buildFixMessage();

            session.sendMessage(new TextMessage(response));
            
            storeMessage.storeFixMessages(payload);
            storeMessage.storeFixMessages(response);
            
        } else {
            session.sendMessage(new TextMessage("FIX message invalid, a required field is missing."));
        }
    }
}
