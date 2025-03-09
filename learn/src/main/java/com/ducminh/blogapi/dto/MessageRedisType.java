package com.ducminh.blogapi.dto;

import com.ducminh.blogapi.constant.ApiMethod;
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
    private ApiMethod apiMethod;

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    private T value;
}
