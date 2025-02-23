package com.ducminh.blogapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SubredditRemoveRequest {
    private String subredditId;
    private String userId;
}
