package com.ducminh.blogapi.service;

import com.ducminh.blogapi.constant.ErrorCode;
import com.ducminh.blogapi.dto.request.*;
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

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class SubredditService {
    @Autowired
    private SubredditMapper subredditMapper;

    @Autowired
    private SubredditRepository subredditRepository;

    @Autowired
    private UserRepository userRepository;

    public SubredditResponse createCommunity(SubredditRequest request) {
        Subreddit subreddit = subredditMapper.toSubreddit(request);
        return subredditMapper.toSubredditResponse(subredditRepository.save(subreddit));
    }

    public void userJoinSubreddit(UserSubredditRequest userSubredditRequest, Principal principal) {
        Subreddit subreddit = subredditRepository.findSubredditById(userSubredditRequest.getSubredditId()).get();
        Set<User> users = subreddit.getUsers();
        User user = userRepository.findByUsername(principal.getName()).get();
        users.add(user);
        subredditRepository.save(subreddit);
    }

    public SubredditResponse getSubredditById(String id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_DATA));
        return subredditMapper.toSubredditResponse(subreddit);
    }

    public void leaveSubreddit(String subredditId, Principal principal) {
        Subreddit subreddit = subredditRepository.findById(subredditId).orElseThrow(() -> new AppException(ErrorCode.INVALID_DATA));
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        subreddit.getUsers().remove(user);
        subredditRepository.save(subreddit);
    }
}


