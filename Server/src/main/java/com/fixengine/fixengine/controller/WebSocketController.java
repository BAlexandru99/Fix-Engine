package com.fixengine.fixengine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebSocketController {

    @GetMapping("/websocket")
    public String websocketPage() {
        return "websocket";  // Numele fișierului Thymeleaf (websocket.html)
    }
}

