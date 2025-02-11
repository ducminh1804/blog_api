package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.dto.request.AuthenticationRequest;
import com.ducminh.blogapi.dto.request.IntrospectRequest;
import com.ducminh.blogapi.dto.response.ApiResponse;
import com.ducminh.blogapi.dto.response.AuthenticationResponse;
import com.ducminh.blogapi.service.AuthenticationService;
import com.ducminh.blogapi.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
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
    ApiResponse<Claims> verifyToken(@RequestBody IntrospectRequest introspectRequest) {
        Claims claims = tokenService.verify(introspectRequest.getToken());
        System.out.println(claims);
        return ApiResponse.<Claims>builder()
                .data(claims)
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

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody IntrospectRequest request) {
        tokenService.logout(request.getToken());
        return ApiResponse.<Void>builder()
                .message("Đăng xuất thành công")
                .build();
    }
}
