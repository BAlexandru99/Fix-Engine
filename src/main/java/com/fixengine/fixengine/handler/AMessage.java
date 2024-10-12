package com.fixengine.fixengine.handler;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.fixengine.fixengine.entity.FixMessage;
import com.fixengine.fixengine.generator.FixMessageGenerator;

@Primary
@Component
public class AMessage implements FixHandler {

    @Override
    public FixMessage handleMessage(FixMessage message) {
       FixMessage response = FixMessageGenerator.createBaseMessage(message);
       if(message.getField(35).equals("A")){
            response.addField(35, "A");
            response.addField(108, message.getField(108));
       }
       return response;
    }
    
}
