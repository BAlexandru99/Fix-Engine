package com.fixengine.fixengine.handler;

import com.fixengine.fixengine.entity.FixMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.stereotype.Component;

@Component
public interface FixHandler {

    FixMessage handleMessage(FixMessage message, WebSocketSession session);
    String getMessageType();
}
