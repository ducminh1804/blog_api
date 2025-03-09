package com.ducminh.blogapi.dto.request;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageRequest implements Serializable {
    private String senderId;
    private String recipentId;
    private String content;
}
