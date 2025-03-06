package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.config.StompPrincipal;
import com.ducminh.blogapi.dto.request.ChatMessage;
import com.ducminh.blogapi.dto.request.MessageRequest;
import com.ducminh.blogapi.repository.jpa.UserRepository;
import com.ducminh.blogapi.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@Slf4j
public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/chat/room/{roomId}")
    public ChatMessage sendGroupMessage(@DestinationVariable String roomId, SimpMessageHeaderAccessor accessor, @Payload ChatMessage message) {
        Principal principal = accessor.getUser();
        String username = principal.getName();
        String senderId = userRepository.findByUsername(username).get().getId();

        simpMessagingTemplate.convertAndSend("/topic/" + roomId, message);

        MessageRequest messageRequest = MessageRequest.builder()
                .senderId(senderId)
                .recipentId(roomId)
                .content(message.getContent())
                .build();
        messageService.saveMessages(messageRequest);
        return message;
    }

    @MessageMapping("/chat/private/{recipentId}")
    public ChatMessage sendPrivateMessage(@DestinationVariable String recipentId, SimpMessageHeaderAccessor accessor, @Payload ChatMessage message) {
        Principal principal = (StompPrincipal) accessor.getUser();
        String username = principal.getName();
        log.info("userId {}", username);
        simpMessagingTemplate.convertAndSendToUser(recipentId, "/queue/messages", message);

        MessageRequest messageRequest = MessageRequest.builder()
                .senderId(message.getSenderId())
                .recipentId(recipentId)
                .content(message.getContent())
                .build();
        messageService.saveMessages(messageRequest);
        return message;
    }

}
