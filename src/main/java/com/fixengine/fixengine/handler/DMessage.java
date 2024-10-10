package com.fixengine.fixengine.handler;

import com.fixengine.fixengine.generator.FixMessageGenerator;
import com.fixengine.fixengine.entity.FixMessage;
import org.springframework.stereotype.Component;

@Component
public class DMessage implements FixHandler {

    @Override
    public FixMessage handleMessage(FixMessage message) {
        FixMessage response = FixMessageGenerator.createBaseMessage();
        if (message.getField(35).equals("D")) {
            response.addField(35, "8"); // MsgType răspuns
            response.addField(39, "2"); // OrdStatus: Filled
            response.addField(34, "1"); // MsgSeqNum
            response.addField(9, response.updateBodyLength()); // Calculăm și actualizăm BodyLength
        }

        return response;
    }
}
