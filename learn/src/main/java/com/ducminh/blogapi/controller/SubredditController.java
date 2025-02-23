package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.dto.request.SubredditRequest;
import com.ducminh.blogapi.dto.request.UserSubredditRequest;
import com.ducminh.blogapi.dto.response.ApiResponse;
import com.ducminh.blogapi.dto.response.SubredditResponse;
import com.ducminh.blogapi.service.SubredditService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    @PutMapping
    ApiResponse<Void> userJoinSubreddit(@RequestBody UserSubredditRequest userSubredditRequest, Principal principal) {
        subredditService.userJoinSubreddit(userSubredditRequest, principal);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder().build();
        return apiResponse;
    }

    @GetMapping()
    ApiResponse<SubredditResponse> getSubById(@RequestParam(name = "id", required = false) String id) {
        SubredditResponse subredditResponse = subredditService.getSubredditById(id);
        ApiResponse<SubredditResponse> apiResponse = ApiResponse.<SubredditResponse>builder()
                .data(subredditResponse)
                .build();
        return apiResponse;
    }

    @DeleteMapping
    ApiResponse<Void> leaveSubreddit(@RequestParam(name = "id") String id, Principal principal) {
        subredditService.leaveSubreddit(id, principal);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .build();
        return apiResponse;
    }

    ;

}
