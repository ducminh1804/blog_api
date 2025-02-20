package com.ducminh.blogapi.controller;

import com.cloudinary.Api;
import com.ducminh.blogapi.dto.request.FollowRequest;
import com.ducminh.blogapi.dto.response.ApiResponse;
import com.ducminh.blogapi.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follows")
public class FollowController {
    @Autowired
    private FollowService followService;

    @PostMapping
    ApiResponse<Void> create(@RequestBody FollowRequest request) {
        followService.create(request);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .build();
        return apiResponse;
    }
}
