package com.fixengine.fixengine.store;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;




@Component

public class storeMessage {

    private String name;

   public void setName(String name){
    this.name = name;
   }

    public void storeFixMessages(String message ){

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    String timestamp = LocalDateTime.now().format(formatter);

    Path path = Paths.get(getName() + " - " + timestamp + ".log");
    
    try(OutputStream writer = new FileOutputStream(path.toString(), true)){
        message = message + System.lineSeparator();
        
        writer.write(message.getBytes((StandardCharsets.UTF_8)));
        writer.flush();
    }catch(IOException e){
        System.err.println("An error occurred while writing to the log file: " + e.getMessage());
    }
    }

    public String getName(){
        return this.name;
    }
}