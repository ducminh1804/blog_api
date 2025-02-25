package com.ducminh.blogapi.dto.response;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageResponse {
    private String id;
    private String senderId;
    private String recipentId;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
}
