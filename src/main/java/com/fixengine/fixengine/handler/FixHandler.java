package com.fixengine.fixengine.handler;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fixengine.fixengine.entity.FixMessage;

@Component
public interface FixHandler {

    public FixMessage handleMessage(FixMessage message);
    
}
