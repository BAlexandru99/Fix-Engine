package com.fixengine.fixengine.generator;

import com.fixengine.fixengine.entity.FixMessage;

public class FixMessageGenerator {
    
    public static FixMessage generateExecutionReport(FixMessage originalOrder){
        FixMessage executionReport = new FixMessage();

        executionReport.addField(8, "FIX.4.4"); 
        executionReport.addField(35, "8");  
        executionReport.addField(49, originalOrder.getField(56));  
        executionReport.addField(56, originalOrder.getField(49));  
        executionReport.addField(11, originalOrder.getField(11));
        executionReport.addField(17, "12345");  
        executionReport.addField(150, "0"); 
        executionReport.addField(39, "2"); 
        executionReport.addField(55, originalOrder.getField(55)); 
        executionReport.addField(54, originalOrder.getField(54)); 
        executionReport.addField(38, originalOrder.getField(38));
        executionReport.addField(44, originalOrder.getField(44)); 

        return executionReport;
    }
}
