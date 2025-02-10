package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.dto.request.RoleRequest;
import com.ducminh.blogapi.dto.response.ApiResponse;
import com.ducminh.blogapi.dto.response.RoleResponse;
import com.ducminh.blogapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> create(@RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = roleService.create(roleRequest);
        ApiResponse<RoleResponse> apiResponse = ApiResponse.<RoleResponse>builder()
                .data(roleResponse)
                .build();
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAll() {
        List<RoleResponse> roleResponses = roleService.getAll();
        ApiResponse<List<RoleResponse>> apiResponse = ApiResponse.<List<RoleResponse>>builder()
                .data(roleResponses)
                .build();
        return apiResponse;
    }
}
