package com.ducminh.blogapi.controller;

import com.cloudinary.Api;
import com.ducminh.blogapi.dto.response.ApiResponse;
import com.ducminh.blogapi.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractMap;
import java.util.Map;

@RestController
@RequestMapping("/redis")

public class HelloRedisController {

    @Autowired
    private RedisService redisService;

    private static final String STRING_KEY_PREFIX = "redis:key:";

    @PostMapping("/strings")
    @ResponseStatus(HttpStatus.CREATED)
    ApiResponse<Void> setString(@RequestBody Map.Entry<String, String> kvp) {
        redisService.setKey(kvp.getKey(), kvp.getValue());
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder().build();
        return apiResponse;
    }

    @GetMapping("/strings/{key}")
    ApiResponse<Map.Entry<String, String>> getString(@PathVariable("key") String key) {
        String value = redisService.getKey(key);
        ApiResponse<Map.Entry<String, String>> apiResponse = ApiResponse.<Map.Entry<String, String>>builder()
                .data(new AbstractMap.SimpleEntry<>(key, value))
                .build();
        return apiResponse;
    }
}
