package com.ducminh.blogapi.constant;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRedisType<T> {
    private String type;
    
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    private T value;
}
