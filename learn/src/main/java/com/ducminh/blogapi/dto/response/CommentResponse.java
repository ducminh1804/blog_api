package com.ducminh.blogapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private int id;
    private String userId;
    private String content;
    private Instant createdAt;
    private int voteDown;
    private int voteUp;
}
