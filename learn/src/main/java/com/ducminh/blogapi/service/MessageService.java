package com.ducminh.blogapi.service;

import com.ducminh.blogapi.dto.request.MessageRequest;
import com.ducminh.blogapi.dto.response.MessageResponse;
import com.ducminh.blogapi.entity.Message;
import com.ducminh.blogapi.entity.User;
import com.ducminh.blogapi.mapper.MessageMapper;
import com.ducminh.blogapi.repository.MessageRepository;
import com.ducminh.blogapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageMapper messageMapper;

    public void saveMessages(MessageRequest request) {
        log.info("request info: {}", request.toString());
        Message message = messageMapper.toMessage(request);
        User user = userRepository.findById(request.getSenderId()).get();
        message.setUser(user);
        log.info("recipentId {}", message.getRecipentId());
        messageRepository.save(message);
    }
}
