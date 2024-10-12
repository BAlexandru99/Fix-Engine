package com.fixengine.fixengine.handler;

import com.fixengine.fixengine.generator.FixMessageGenerator;
import com.fixengine.fixengine.entity.FixMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DMessage implements FixHandler {

    @Override
    public FixMessage handleMessage(FixMessage message) {
        FixMessage response = FixMessageGenerator.createBaseMessage(message);
        if (message.getField(35).equals("D")) {
            response.addField(35, "8");
            response.addField(39, "2");  
           
        }

        return response;
    }
}
