package com.fixengine.fixengine.store;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class storeMessage {
    private final List<String> messages = new ArrayList<>();

    public void storeFixMessages(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
