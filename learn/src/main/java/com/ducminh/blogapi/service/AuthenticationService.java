package com.ducminh.blogapi.service;

import com.ducminh.blogapi.config.SecurityConfig;
import com.ducminh.blogapi.dto.request.AuthenticationRequest;
import com.ducminh.blogapi.dto.request.IntrospectRequest;
import com.ducminh.blogapi.dto.request.RefreshTokenRequest;
import com.ducminh.blogapi.dto.response.AuthenticationResponse;
import com.ducminh.blogapi.entity.User;
import com.ducminh.blogapi.exception.AppException;
import com.ducminh.blogapi.exception.ErrorCode;
import com.ducminh.blogapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        boolean authenticate = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticate) {
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }

        String token = tokenService.generateToken(request.getUsername());
        String refreshToken = tokenService.generateRefreshToken(request.getUsername());
        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .authenticate(authenticate)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authentication.getName()); // Lấy username đã đăng nhập

        } catch (AuthenticationException exception) {
            throw new AppException(ErrorCode.INVALID_DATA);
        } catch (Exception ex) {
            throw ex;
        }

        String token = tokenService.generateToken(request.getUsername());
        String refreshToken = tokenService.generateRefreshToken(request.getUsername());

        System.out.println(token);
        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .authenticate(true)
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String username = null;
        String newToken = null;
        String newRefreshToken = null;
        boolean authenticate = false;

        try {
            username = tokenService.extractUsernameRefreshToken(refreshToken);
            newToken = tokenService.generateToken(username);
            newRefreshToken = tokenService.generateRefreshToken(username);
            authenticate = true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return AuthenticationResponse.builder()
                .token(newToken)
                .refreshToken(newRefreshToken)
                .authenticate(authenticate)
                .build();
    }

}


