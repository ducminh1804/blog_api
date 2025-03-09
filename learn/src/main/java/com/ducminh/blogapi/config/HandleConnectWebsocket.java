package com.ducminh.blogapi.config;

import com.ducminh.blogapi.dto.UserStatusStomp;
import com.ducminh.blogapi.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class HandleConnectWebsocket {
    @Autowired
    private RedisOperations operations;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    private FollowService followService;
    private static final String USER_STATUS_KEY_PREFIX = "user:status:";
    // TTL key được đặt là 5 phút để tránh rò rỉ bộ nhớ
    private static final long STATUS_TTL = 5; // đơn vị: phút

    private static final String ONLINE = "ONLINE";
    private static final String OFFLINE = "OFFLINE";

    @EventListener

    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(event.getMessage(), StompHeaderAccessor.class);
        log.info("da ket noi, info: {}", accessor.getUser().getName());
        String userId = accessor.getUser().getName();
        markUserOnline(userId);
        notifyFollower(userId, ONLINE);
    }

    @EventListener
    public void handleWebSocketDisConnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(event.getMessage(), StompHeaderAccessor.class);
        log.info("da ngat ket noi, info: {}", accessor.getUser().getName());
        String userId = accessor.getUser().getName();
        markUserOffline(userId);
    }

    public void markUserOnline(String userId) {
        String key = USER_STATUS_KEY_PREFIX + userId;
        Map<String, Object> statusInfo = new HashMap<>();
        statusInfo.put("status", "online");
        statusInfo.put("lastActiveTimestamp", Instant.now().toEpochMilli());

        // Lưu vào Redis và set thời gian hết hạn cho key
        operations.opsForHash().putAll(key, statusInfo);
        operations.expire(key, STATUS_TTL, TimeUnit.MINUTES);
    }

    public void markUserOffline(String userId) {
        String key = USER_STATUS_KEY_PREFIX + userId;
        Map<String, Object> statusInfo = new HashMap<>();
        statusInfo.put("status", "offline");
        statusInfo.put("lastActiveTimestamp", Instant.now().toEpochMilli());

        operations.opsForHash().putAll(key, statusInfo);
        operations.expire(key, STATUS_TTL, TimeUnit.MINUTES);
    }


    public void notifyFollower(String userId, String status) {
        List<String> followerIds = followService.findAllFollowingId(userId);
        for (String id : followerIds) {
            UserStatusStomp userStatusStomp = UserStatusStomp.builder()
                    .userId(id)
                    .status(status)
                    .build();
            simpMessagingTemplate.convertAndSendToUser(id, "/queue/messages", userStatusStomp);
        }
    }
}
