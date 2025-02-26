package com.ducminh.blogapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate template;

    public void setKey(String key, String value) {
        template.opsForValue().set(key, value);
    }

    public String getKey(String key) {
        return template.opsForValue().get(key);
    }
}
