package com.client;

import javax.websocket.*;

import com.client.model.FixMessage;
import com.client.template.FixMessageGenerator;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ClientEndpoint
public class WebSocket {
    private Session session;

    // Metoda care este apelata atunci când conexiunea este deschisa
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        System.out.println("Connected to the server.");
        startHeartBeat();

    }
    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received from server: " + message);
    }

    private void startHeartBeat() {
        //executor programabil cu un singur fir de execuție
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() ->{
            try{
                session.getBasicRemote().sendText(sendLogOn());
            }catch (IOException e){
                e.printStackTrace();
            }
        },0 , 30, TimeUnit.SECONDS);
    }
    @OnClose
    public void onClose(Session session, CloseReason reason){
        System.out.println("Connection closed: " + reason);
    }

    public String sendLogOn(){
        Scanner scan = new Scanner(System.in);

        System.out.print("COMPANY: ");
        String companyName = scan.nextLine();

        FixMessageGenerator fixMessageGenerator = new FixMessageGenerator();

        FixMessage message = fixMessageGenerator.generateMessage();

        message.addField(35, "A");
        message.addField(49, companyName);
        message.addField(108, "30");

        System.out.println(message.buildFixMessage());
        return message.buildFixMessage();
    }
}
