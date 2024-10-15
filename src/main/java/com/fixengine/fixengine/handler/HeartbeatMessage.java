// package com.fixengine.fixengine.handler;

// import org.springframework.stereotype.Component;

// import com.fixengine.fixengine.entity.FixMessage;
// import com.fixengine.fixengine.generator.FixMessageGenerator;


// @Component
// public class HeartbeatMessage implements FixHandler{

//     @Override
//     public FixMessage handleMessage(FixMessage message) {
//         System.out.println("Received MsgType: " + message.getField(35));
//         FixMessage response = FixMessageGenerator.createBaseMessage(message);
//         if(message.getField(35).equals("0")){
//             response.addField(35, "0");
//         }
//         return response;
//     }

//     @Override
//     public String getMessageType() {
//        return "0";
//     }
    
// }
