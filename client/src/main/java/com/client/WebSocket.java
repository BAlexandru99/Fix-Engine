package com.client;

import javax.websocket.*;
import java.io.IOException;
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
                session.getBasicRemote().sendText("8=FIX.4.2|9=112|35=A|49=BuySide|56=SellSide|34=1|52=20241015-15:00:00|108=30|10=178|");
            }catch (IOException e){
                e.printStackTrace();
            }
        },0 , 30, TimeUnit.SECONDS);
    }
    @OnClose
    public void onClose(Session session, CloseReason reason){
        System.out.println("Connection closed: " + reason);
    }
}
