package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.dto.request.ChatMessage;
import com.ducminh.blogapi.dto.request.ChatRequest;
import com.ducminh.blogapi.dto.request.MessageRequest;
import com.ducminh.blogapi.dto.response.ChatResponse;
import com.ducminh.blogapi.repository.UserRepository;
import com.ducminh.blogapi.service.MessageService;
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

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/chat/room/{roomId}")
    public ChatMessage sendGroupMessage(@DestinationVariable String roomId, SimpMessageHeaderAccessor accessor, @Payload ChatMessage message) {
        Principal principal = accessor.getUser();
        String username = principal.getName();
        String senderId = userRepository.findByUsername(username).get().getId();
        message.setSender(senderId);

        simpMessagingTemplate.convertAndSend("/topic/" + roomId, message);

        MessageRequest messageRequest = MessageRequest.builder()
                .senderId(senderId)
                .recipentId(roomId)
                .content(message.getContent())
                .build();
        messageService.saveMessages(messageRequest);
        return message;
    }

    @MessageMapping("/chat/private/{recipent}")
    public ChatMessage sendPrivateMessage(@DestinationVariable String recipent, SimpMessageHeaderAccessor accessor, @Payload ChatMessage message) {
        Principal principal = accessor.getUser();
        String username = principal.getName();
        String senderId = userRepository.findByUsername(username).get().getId();
        String recipentId = userRepository.findByUsername(recipent).get().getId();
        simpMessagingTemplate.convertAndSendToUser(recipent, "/queue/messages", message);

        MessageRequest messageRequest = MessageRequest.builder()
                .senderId(senderId)
                .recipentId(recipentId)
                .content(message.getContent())
                .build();
        messageService.saveMessages(messageRequest);
        return message;
    }

}
