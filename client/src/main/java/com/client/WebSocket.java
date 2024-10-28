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
    private String companyName;
    private boolean logonSent = false;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to the server.");
        Scanner scan = new Scanner(System.in);
        System.out.print("CompID: ");
        this.companyName = scan.nextLine();
        startHeartBeat();
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received from server: " + message);
        if(message.contains("35=A")){
            System.out.println("Connection establish!");
        }
    }

    private void startHeartBeat() {
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(() -> {
                try {
                    if(!logonSent){
                        String logonMessage = sendLogOn(companyName);
                        session.getBasicRemote().sendText(logonMessage);
                        logonSent = true;
                    }else{
                        String heartBeat = heartBeat(companyName);
                        session.getBasicRemote().sendText(heartBeat);
                    }
                } catch (IOException e) {
                    System.err.println("Error sending heartbeat: " + e.getMessage());
                }
             }, 0, 30, TimeUnit.SECONDS);
        }


    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Connection closed: " + reason);
    }

    public String sendLogOn(String companyName) {

        FixMessageGenerator fixMessageGenerator = new FixMessageGenerator();
        FixMessage message = fixMessageGenerator.generateMessage();
    

        message.addField(35, "A"); 
        message.addField(49, companyName);
        message.addField(108, "30"); 

        int bodyLength = fixMessageGenerator.calculateBodyLength(message);
        message.addField(9, String.valueOf(bodyLength));

        String fixMessage = message.buildFixMessage();
        System.out.println("Sending message: " + fixMessage);
        return fixMessage;
    }

    public String heartBeat(String companyName){
        FixMessageGenerator fixMessageGenerator = new FixMessageGenerator();
        FixMessage message = fixMessageGenerator.generateMessage();
        message.addField(35, "0");
        message.addField(49, companyName);
        int bodyLength = fixMessageGenerator.calculateBodyLength(message);
        message.addField(9, String.valueOf(bodyLength));
        String fixMessage = message.buildFixMessage();
        System.out.println("Sending message: " + fixMessage);
        return fixMessage;
    }


}