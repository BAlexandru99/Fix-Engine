package com.fixengine.fixengine.validator;

import com.fixengine.fixengine.entity.FixMessage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FixMessageValidator {

    // Define mandatory tags for specific message types
    private static final Set<Integer> LOGON_MANDATORY_TAGS = new HashSet<>(Arrays.asList(8, 35, 49, 56, 34, 52));
    private static final Set<Integer> HEARTBEAT_MANDATORY_TAGS = new HashSet<>(Arrays.asList(8, 35, 34));
    private static final Set<Integer> OTHER_MANDATORY_TAGS = new HashSet<>(Arrays.asList(8, 35));

    // Validate FIX message based on message type
    public static boolean validateFixMessage(FixMessage message) {
        String msgType = message.getField(35);
        
        switch (msgType) {
            case "A": // Logon message
                return validateMandatoryFields(message, LOGON_MANDATORY_TAGS);
            case "0": // Heartbeat message
                return validateMandatoryFields(message, HEARTBEAT_MANDATORY_TAGS);
            case "D": // Order message (example)
                // Define mandatory tags for order messages if needed
                return validateMandatoryFields(message, OTHER_MANDATORY_TAGS);
            // Add more cases for other message types as necessary
            default:
                return false; // Unsupported message type
        }
    }

    // Check if all mandatory fields are present in the message
    private static boolean validateMandatoryFields(FixMessage message, Set<Integer> mandatoryTags) {
        for (Integer tag : mandatoryTags) {
            if (message.getField(tag) == null) {
                System.out.println("Missing mandatory tag: " + tag);
                return false; // A mandatory tag is missing
            }
        }
        return true; // All mandatory tags are present
    }
}
