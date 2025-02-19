package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.dto.response.ApiResponse;
import com.ducminh.blogapi.dto.response.CloudinaryResponse;
import com.ducminh.blogapi.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class CloudinaryController {
    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/image")
    public ApiResponse<Map> uploadImage(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Map result = cloudinaryService.uploadImage(file);
        Map toResponse = CloudinaryResponse.toCloudinayResponse(result);
        ApiResponse apiResponse = ApiResponse.<Map>builder()
                .data(toResponse)
                .build();
        return apiResponse;
    }

    @PostMapping("/video")
    public ApiResponse<Map> uploadVideo(@RequestParam("file") MultipartFile file) throws IOException {
        Map result = cloudinaryService.uploadVideo(file);
        Map toResponse = CloudinaryResponse.toCloudinayResponse(result);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(toResponse)
                .build();
        return apiResponse;
    }

    
}
