package com.fixengine.fixengine.generator;

import com.fixengine.fixengine.entity.FixMessage;

public class FixMessageGenerator {
    
    public static FixMessage createBaseMessage(){
        FixMessage message = new FixMessage();

        message.addField(8, "FIX.4.2");
        message.addField(9, "");
        message.addField(35, "");
        message.addField(34, "");

        return message;
    }
}
