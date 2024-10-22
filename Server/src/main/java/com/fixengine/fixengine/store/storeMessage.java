package com.fixengine.fixengine.store;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Component;



@Component

public class storeMessage {

    public void storeFixMessages(String message){

    Path path = Paths.get("file.log");
    
    try(OutputStream writer = new FileOutputStream(path.toString(), true)){
        message = message + System.lineSeparator();
        
        writer.write(message.getBytes((StandardCharsets.UTF_8)));
        writer.flush();
    }catch(IOException e){
        System.err.println("An error occurred while writing to the log file: " + e.getMessage());
    }
    }
}