package com.ducminh.blogapi.service;

import com.ducminh.blogapi.constant.MessageRedisType;
import com.ducminh.blogapi.constant.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

@Service
public class PublisherMessage {
    @Autowired
    private RedisOperations operations;

    public void send(MessageRedisType messageType) {
        operations.convertAndSend(Topic.Post.name(), messageType);
    }
}
