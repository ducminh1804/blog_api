package com.ducminh.blogapi.dto.response;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageResponse implements Serializable {
    private String id;
    private String senderId;
    private String recipentId;
    private String content;

}
