package com.ducminh.blogapi.service;

import com.ducminh.blogapi.constant.MessageRedisType;
import com.ducminh.blogapi.entity.Post;
import com.ducminh.blogapi.entity.PostEs;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class SubscriberMessage implements MessageListener {
    @Autowired
    private ElasticsearchService elasticsearchService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MessageRedisType<PostEs> messageType = objectMapper.readValue(message.getBody(), new TypeReference<MessageRedisType<PostEs>>() {
            });
            String type = messageType.getType();
            Object value = messageType.getValue();
            PostEs postEs = messageType.getValue();
            log.info("type {}", type);
            log.info("value {}", value);
            switch (type) {
                case "Post":
                    elasticsearchService.save(postEs);
                    log.info("success {}", "insert to es: " + value);
                default:
                    break;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
