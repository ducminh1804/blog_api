package com.ducminh.blogapi.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageRequest {
    private String senderId;
    private String recipentId;
    private String content;
}
