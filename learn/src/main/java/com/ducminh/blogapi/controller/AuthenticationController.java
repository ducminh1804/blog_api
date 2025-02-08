package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.dto.request.AuthenticationRequest;
import com.ducminh.blogapi.dto.response.ApiResponse;
import com.ducminh.blogapi.dto.response.AuthenticationResponse;
import com.ducminh.blogapi.service.AuthenticationService;
import com.ducminh.blogapi.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;

    @Autowired
    private JwtService tokenService;


    @PostMapping("/get-token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticate = service.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .data(
                        authenticate
                )
                .build();
    }

    @PostMapping("/verify")
    ApiResponse<Boolean> verifyToken(@RequestBody String token) {
        boolean check = tokenService.verify(token);
        System.out.println(check);
        return ApiResponse.<Boolean>builder()
                .data(check)
                .build();
    }

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticate = service.login(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .data(
                        authenticate
                )
                .build();
    }
}
