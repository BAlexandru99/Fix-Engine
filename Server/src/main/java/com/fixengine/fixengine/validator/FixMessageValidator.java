package com.fixengine.fixengine.validator;

import java.util.Arrays;
import java.util.List;

import com.fixengine.fixengine.entity.FixMessage;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FixMessageValidator {
    
    private static final List<Integer> requiredTags = Arrays.asList(8, 35, 49, 56, 11, 55, 54, 38, 40);

    public static boolean validateFixMessage(FixMessage fixMessage){
        String messageType = fixMessage.getField(35);
        if (messageType == null){
            return false;
        }
        if(messageType.equals("D")) {
            for(int tag : requiredTags){
                if(fixMessage.getField(tag) == null){
                    System.out.println("FIX message missing required tag" + tag);
                    return false;
                }
            }
        }
        return true;
    }

}
