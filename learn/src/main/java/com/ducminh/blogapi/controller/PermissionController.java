package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.dto.request.PermissionRequest;
import com.ducminh.blogapi.dto.response.ApiResponse;
import com.ducminh.blogapi.dto.response.PermissionResponse;
import com.ducminh.blogapi.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest permissionRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(permissionService.create(permissionRequest))
                .build();
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAll() {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(permissionService.getAll())
                .build();
        return apiResponse;
    }

    @DeleteMapping
    public ApiResponse<PermissionResponse> delete(@RequestBody PermissionRequest permissionRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(permissionService.delete(permissionRequest))
                .build();
        return apiResponse;
    }
}
