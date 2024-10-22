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

    private final storeMessage storeMessage;
    private final FixHandlerRouter handlerRouter;
    private final Object lock = new Object(); // Object for synchronization

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received from the client: \n" + payload);
        FixMessage fixMessage = FixMessage.parseFixMessage(payload);

        if (FixMessageValidator.validateFixMessage(fixMessage)) {
            FixMessage responseMessage = handlerRouter.routeMessage(fixMessage, session);
            String response = responseMessage.buildFixMessage();

            // Synchronization for sending the message
            synchronized (lock) {
                session.sendMessage(new TextMessage(response));
                System.out.println("Sending message:  \n" + response);
            }

            // Store sent and received messages
            storeMessage.storeFixMessages("Received from client: " + payload);
            storeMessage.storeFixMessages("Sent from the server: " + response);
        } else {
            // Synchronization for sending the error message
            synchronized (lock) {
                session.sendMessage(new TextMessage("FIX message invalid, a required field is missing."));
            }
        }
    }
}
