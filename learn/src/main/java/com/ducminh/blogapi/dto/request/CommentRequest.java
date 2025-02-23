package com.ducminh.blogapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private int parentId;
    private String userId;
    private String postId;
    private String content;
}
