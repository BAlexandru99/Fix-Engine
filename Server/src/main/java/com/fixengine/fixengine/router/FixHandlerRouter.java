package com.fixengine.fixengine.router;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.fixengine.fixengine.entity.FixMessage;
import com.fixengine.fixengine.handler.FixHandler;

@Component
public class FixHandlerRouter {
    private final Map<String, FixHandler> handlerMap = new HashMap<>();

    public FixHandlerRouter(List<FixHandler> handlers) {
        for (FixHandler handler : handlers) {
            handlerMap.put(handler.getMessageType(), handler);
        }
    }

    public FixMessage routeMessage(FixMessage message, WebSocketSession session) {
        String msgType = message.getField(35);
        FixHandler handler = handlerMap.get(msgType);
        if (handler != null) {
            return handler.handleMessage(message, session);
        } else {
            throw new IllegalArgumentException("No handler found for message type: " + msgType);
        }
    }
}
