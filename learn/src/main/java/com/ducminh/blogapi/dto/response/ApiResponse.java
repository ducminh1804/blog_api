package com.ducminh.blogapi.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
//builder.default xac dinh gia tri default khi ta k truyen cho no khi builder()
public class ApiResponse<T> {
    @Builder.Default
    private int code = 9000;
    private String message;
    private T data;
}
