package com.ducminh.blogapi.service;

import com.ducminh.blogapi.dto.request.FollowRequest;
import com.ducminh.blogapi.dto.response.FollowResponse;
import com.ducminh.blogapi.entity.Follow;
import com.ducminh.blogapi.entity.FollowId;
import com.ducminh.blogapi.repository.jpa.FollowRepository;
import com.ducminh.blogapi.repository.jpa.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FollowService {
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    public void create(FollowRequest request) {
        Follow follow = new Follow();
        follow.setId(new FollowId(request.getFollowingId(), request.getFollowerId()));
        followRepository.save(follow);
    }

    public List<FollowResponse> getAllFollowerById(Principal principal) {
        String userId = userRepository.findByUsername(principal.getName()).get().getId();
        List<FollowResponse> followResponses = followRepository.findAllFollowerById(userId)
                .map(list -> list.stream()
                        .map(item -> new FollowResponse(item.getFollowingId(), item.getUsername()))
                        .collect(Collectors.toList())
                )
                .orElse(Collections.emptyList());
        return followResponses;
    }

    public List<String> findAllFollowingId(String followingId) {
        List<String> list = followRepository.findAllByFollowingId(followingId);
        return list;
    }
}
