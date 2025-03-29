package com.ducminh.blogapi.service;

import com.ducminh.blogapi.dto.request.MessageRequest;
import com.ducminh.blogapi.dto.response.MessageResponse;
import com.ducminh.blogapi.entity.Message;
import com.ducminh.blogapi.entity.User;
import com.ducminh.blogapi.mapper.MessageMapper;
import com.ducminh.blogapi.repository.jpa.MessageRepository;
import com.ducminh.blogapi.repository.jpa.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@EnableCaching
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private RedisOperations operations;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String key_queue_msg = "chat:queue";
    private static final String key_queue_msg_backup = "chat:queue_backup";
    private static final int BATCH_SIZE = 1;

    public Message saveMessages(MessageRequest request) {
        log.info("request info: {}", request.toString());
        Message message = messageMapper.toMessage(request);
        User user = userRepository.findById(request.getSenderId()).get();
        message.setUser(user);
        log.info("recipentId {}", message.getRecipentId());
        return messageRepository.save(message);
    }

    public void saveMessagesToRedis(MessageRequest request) {
        operations.opsForList().leftPush(key_queue_msg, request);
    }

    @Transactional
    @Scheduled(fixedDelay = 3000)
    public void batchService() {
        Long queueSize = operations.opsForList().size(key_queue_msg);
        if (queueSize < BATCH_SIZE) {
            return;
        }

        List<Object> objects = operations.opsForList().range(key_queue_msg, 0, BATCH_SIZE - 1);
        if (objects == null || objects.isEmpty()) {
            return;
        }

        List<MessageRequest> messageRequests = objects.stream()
                .map(object -> {
                    return objectMapper.convertValue(object, MessageRequest.class);
                })
                .collect(Collectors.toList());

        log.info("list msg {}", messageRequests.get(0).toString());
        if (!messageRequests.isEmpty()) {
            List<Message> messages = messageRequests.stream()
                    .map(messageMapper::toMessage)
                    .collect(Collectors.toList());
            messageRepository.saveAll(messages);
            operations.opsForList().trim(key_queue_msg, BATCH_SIZE, -1);
        }
    }

    @Cacheable(value = "message", key = "#senderId + ':'+#recepientId + ':' + #instant")
    public List<MessageResponse> getMessagesPagination(String senderId, String recepientId, Instant instant) {
        List<MessageResponse> messages = messageRepository.getMessagesPagination(senderId, recepientId, instant)
                .stream()
                .map(messageMapper::toMessageResponse)
                .collect(Collectors.toList());
        return messages;
    }
}
