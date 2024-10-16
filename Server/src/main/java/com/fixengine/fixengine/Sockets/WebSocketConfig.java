package com.fixengine.fixengine.Sockets;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.AllArgsConstructor;


@EnableWebSocket
@Configuration
@AllArgsConstructor

public class WebSocketConfig implements WebSocketConfigurer {
    
    MyWebSocketHandler socketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, "/fix-endpoint").setAllowedOriginPatterns("*");
    }
    
}
