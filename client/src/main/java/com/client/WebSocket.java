package com.client;

import javax.websocket.*;
import com.client.model.FixMessage;
import com.client.template.FixMessageGenerator;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ClientEndpoint
public class WebSocket {
    private Session session;
    private String companyName;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to the server.");
        Scanner scan = new Scanner(System.in);
        System.out.print("COMPANY: ");
        this.companyName = scan.nextLine();
        startHeartBeat();
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received from server: " + message);
        // Optionally parse and process incoming messages here
    }


    private void startHeartBeat() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            try {
                String logonMessage = sendLogOn(companyName);
                session.getBasicRemote().sendText(logonMessage);
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
    

        message.addField(35, "A"); // Logon message type
        message.addField(49, companyName); // Sender Comp ID
        message.addField(108, "30"); // Heartbeat interval

        int bodyLength = fixMessageGenerator.calculateBodyLength(message);
        message.addField(9, String.valueOf(bodyLength));

        String fixMessage = message.buildFixMessage();
        System.out.println("Sending message: " + fixMessage);
        return fixMessage;
    }

}
