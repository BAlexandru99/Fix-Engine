package com.client;


import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

import com.client.GUI.FixOrderGUI;

import java.net.URI;


public class App
{
    public static void main(String[] args) {
        FixOrderGUI gui = new FixOrderGUI();
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            URI uri = new URI("ws://localhost:8080/fix");
            container.connectToServer(WebSocket.class , uri);
            gui.startGUI();
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}