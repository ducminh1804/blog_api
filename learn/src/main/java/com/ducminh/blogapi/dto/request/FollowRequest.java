package com.ducminh.blogapi.dto.request;

import com.ducminh.blogapi.constant.PrivacyEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowRequest {
    private String followingId;
    private String followerId;
}
