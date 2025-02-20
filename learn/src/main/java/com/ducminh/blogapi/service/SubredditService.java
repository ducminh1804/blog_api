package com.ducminh.blogapi.service;

import com.ducminh.blogapi.constant.ErrorCode;
import com.ducminh.blogapi.dto.request.AuthenticationRequest;
import com.ducminh.blogapi.dto.request.RefreshTokenRequest;
import com.ducminh.blogapi.dto.request.SubredditRequest;
import com.ducminh.blogapi.dto.response.AuthenticationResponse;
import com.ducminh.blogapi.dto.response.SubredditResponse;
import com.ducminh.blogapi.entity.Subreddit;
import com.ducminh.blogapi.entity.User;
import com.ducminh.blogapi.exception.AppException;
import com.ducminh.blogapi.mapper.SubredditMapper;
import com.ducminh.blogapi.repository.SubredditRepository;
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
public class SubredditService {
    @Autowired
    private SubredditMapper subredditMapper;

    @Autowired
    private SubredditRepository subredditRepository;

    public SubredditResponse createCommunity(SubredditRequest request) {
        Subreddit subreddit = subredditMapper.toSubreddit(request);
        return subredditMapper.toSubredditResponse(subredditRepository.save(subreddit));
    }
}


