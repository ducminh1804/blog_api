package com.ducminh.blogapi.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRequest {
    private String senderId;
    private String recipentId;
    private String content;
}
