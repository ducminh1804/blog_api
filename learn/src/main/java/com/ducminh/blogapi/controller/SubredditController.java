package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.dto.request.SubredditRequest;
import com.ducminh.blogapi.dto.response.ApiResponse;
import com.ducminh.blogapi.dto.response.SubredditResponse;
import com.ducminh.blogapi.service.SubredditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/community")
public class SubredditController {
    @Autowired
    private SubredditService subredditService;


    @PostMapping
    ApiResponse<SubredditResponse> createCommunity(@RequestBody SubredditRequest request) {
        ApiResponse<SubredditResponse> apiResponse = ApiResponse.<SubredditResponse>builder()
                .data(subredditService.createCommunity(request))
                .build();
        return apiResponse;
    }
}
