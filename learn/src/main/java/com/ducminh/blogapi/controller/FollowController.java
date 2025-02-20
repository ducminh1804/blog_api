package com.ducminh.blogapi.controller;

import com.cloudinary.Api;
import com.ducminh.blogapi.dto.request.FollowRequest;
import com.ducminh.blogapi.dto.response.ApiResponse;
import com.ducminh.blogapi.dto.response.FollowResponse;
import com.ducminh.blogapi.service.FollowService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

    @GetMapping
    ApiResponse<List<FollowResponse>> getAllFollowerById(Principal principal) {
        ApiResponse<List<FollowResponse>> apiResponse = ApiResponse.<List<FollowResponse>>builder()
                .data(followService.getAllFollowerById(principal))
                .build();
        return apiResponse;
    }
}
