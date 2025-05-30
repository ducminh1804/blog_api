package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.dto.request.CommentRequest;
import com.ducminh.blogapi.dto.response.ApiResponse;
import com.ducminh.blogapi.dto.response.CommentResponse;
import com.ducminh.blogapi.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comments")
@Slf4j
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    ApiResponse<Void> createComment(@RequestBody CommentRequest request) {
        commentService.createComment(request);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder().build();
        return apiResponse;
    }

    @GetMapping("/{postId}")
    ApiResponse<Page<CommentResponse>> findByPostId(
            @PathVariable String postId,
            @RequestParam(defaultValue = "0") int parentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentResponse> commentResponses = commentService.findByPostId(postId, parentId, pageable);
        ApiResponse<Page<CommentResponse>> apiResponse = ApiResponse.<Page<CommentResponse>>builder()
                .data(commentResponses)
                .build();
        return apiResponse;
    }

    @GetMapping("/flag/{parentId}")
    ApiResponse<Long> checkChildComment(@PathVariable String parentId) {
        ApiResponse<Long> apiResponse = ApiResponse.<Long>builder()
                .data(commentService.checkChildComment(parentId))
                .build();
        return apiResponse;
    }
}
