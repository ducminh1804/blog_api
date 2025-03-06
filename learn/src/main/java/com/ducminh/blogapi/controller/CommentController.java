package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.dto.request.CommentRequest;
import com.ducminh.blogapi.dto.response.ApiResponse;
import com.ducminh.blogapi.dto.response.CommentResponse;
import com.ducminh.blogapi.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    ApiResponse<List<CommentResponse>> findByPostId(
            @PathVariable String postId,
            @RequestParam(defaultValue = "0") int parentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);

        long startTotal = System.nanoTime();

        long startCache = System.nanoTime();
        List<CommentResponse> commentResponses = commentService.findByPostId(postId, parentId, pageable);
        long endCache = System.nanoTime();

        long startSerialization = System.nanoTime();
        ApiResponse<List<CommentResponse>> apiResponse = ApiResponse.<List<CommentResponse>>builder()
                .data(commentResponses)
                .build();
        long endSerialization = System.nanoTime();

        long endTotal = System.nanoTime();

        System.out.println("Redis Query Time: " + (endCache - startCache) / 1_000_000 + " ms");
        System.out.println("Serialization Time: " + (endSerialization - startSerialization) / 1_000_000 + " ms");
        System.out.println("Total API Response Time: " + (endTotal - startTotal) / 1_000_000 + " ms");

        return apiResponse;
    }
}
