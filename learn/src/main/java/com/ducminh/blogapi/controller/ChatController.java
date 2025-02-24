package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.dto.request.ChatMessage;
import com.ducminh.blogapi.dto.request.ChatRequest;
import com.ducminh.blogapi.dto.response.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@Slf4j
public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable String roomId, SimpMessageHeaderAccessor accessor, @Payload ChatMessage message) {
//        String username = accessor.getUser().getName();
        String username = (String) accessor.getSessionAttributes().get("USER_SESSION_ATTR");
        message.setSender(username);
        simpMessagingTemplate.convertAndSendToUser(username, "/topic/" + roomId, message);
        return message;
    }


    @MessageMapping("/private-message")
    public void send(@Payload String username) {
        String message = "Hello from " + username;

        simpMessagingTemplate.convertAndSendToUser(username, "/queue/messages", message);
    }
}
