package com.ducminh.blogapi.service;

import com.ducminh.blogapi.constant.ApiMethod;
import com.ducminh.blogapi.dto.MessageRedisType;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // Sử dụng phương thức tổng quát để xử lý việc giải mã
            MessageRedisType<?> messageType = deserializeMessage(message);

            ApiMethod apiMethod = messageType.getApiMethod();
            Object value = messageType.getValue();

            log.info("type {}", apiMethod);
            log.info("value {}", value.getClass().getName());

            switch (apiMethod) {
                case POST:
                    elasticsearchService.save(value);
                    log.info("success {}", "insert to es: " + value);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private <T> ReturnType methodName(Parameters) { ... }

    private <T> MessageRedisType<T> deserializeMessage(Message message) throws IOException {
        return objectMapper.readValue(message.getBody(), new TypeReference<MessageRedisType<T>>() {
        });
    }
}

//{
//        "type": "PostApi",
//        "value": {
//        "id": 1,
//        "title": "My Post"
//        }
//        }
//MessageRedisType<PostEs> message = deserializeMessage(jsonData); ===>  MessageRedisType value = MessageRedisType(type="PostApi", value=LinkedHashMap);
// bi chuyen thanh linkedhashmap, dung TypeReference<MessageRedisType<T>>() {} de giu nguyen kieu du lieu