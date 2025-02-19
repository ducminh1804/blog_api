package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.dto.request.VoteRequest;
import com.ducminh.blogapi.dto.response.ApiResponse;
import com.ducminh.blogapi.dto.response.VoteResponse;
import com.ducminh.blogapi.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @PutMapping
    ApiResponse<VoteResponse> updateVote(@RequestBody VoteRequest request, Principal principal) {
        ApiResponse<VoteResponse> apiResponse = ApiResponse.<VoteResponse>builder()
                .data(voteService.updateVote(request, principal))
                .build();
        return apiResponse;
    }
}
