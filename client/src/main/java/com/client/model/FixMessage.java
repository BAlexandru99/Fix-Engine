package com.client.model;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FixMessage {

    private Map<Integer, String> message;


    public FixMessage(){
        message = new LinkedHashMap<>();
    }

    public void addField(int key , String value){
        message.put(key, value);
    }

    public String getValue(int key){
        return message.get(key);
    }

    public Map<Integer,String> getMessage() {
        return this.message;
    }

    public void getMessage(Map<Integer,String> message) {
        this.message = message;
    }

}