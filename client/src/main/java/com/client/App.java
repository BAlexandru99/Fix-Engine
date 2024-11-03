package com.client;


import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import com.client.GUI.FixOrderGUI;
import java.net.URI;

public class App
{
    public static void main(String[] args) {
        
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        WebSocket webSocket = new WebSocket();
        try {
            URI uri = new URI("ws://192.168.1.106:8080/fix");
            container.connectToServer(webSocket , uri);
            FixOrderGUI gui = new FixOrderGUI(webSocket);
            gui.startGUI();
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}