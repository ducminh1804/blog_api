package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.dto.request.PostRequest;
import com.ducminh.blogapi.dto.response.ApiResponse;
import com.ducminh.blogapi.dto.response.PostResponse;
import com.ducminh.blogapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/text")
    ApiResponse<PostResponse> createPost(@RequestBody PostRequest request, Principal principal) {
        PostResponse postResponse = postService.createPost(request, principal);
        ApiResponse<PostResponse> apiResponse = ApiResponse.<PostResponse>builder()
                .data(postResponse)
                .build();
        return apiResponse;
    }

    @DeleteMapping("/{postId}")
    ApiResponse<Void> deletePost(@PathVariable("postId") String postId) {
        postService.deletePost(postId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder().build();
        return apiResponse;
    }

    @PostMapping("/image")
    ApiResponse<PostResponse> createPostImage(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "kind", required = false) String kind,
            @RequestParam(value = "tags", required = false) List<String> tags,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Principal principal) throws IOException {
        PostResponse postResponse = postService.createPostImage(title, kind, tags, file, principal);
        ApiResponse<PostResponse> apiResponse = ApiResponse.<PostResponse>builder()
                .data(postResponse)
                .build();
        return apiResponse;
    }

    @PostMapping("/video")
    ApiResponse<PostResponse> createPostVideo(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "kind", required = false) String kind,
            @RequestParam(value = "tags", required = false) List<String> tags,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Principal principal) throws IOException {
        PostResponse postResponse = postService.createPostVideo(title, kind, tags, file, principal);
        ApiResponse<PostResponse> apiResponse = ApiResponse.<PostResponse>builder()
                .data(postResponse)
                .build();
        return apiResponse;
    }

}
