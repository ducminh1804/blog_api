package com.ducminh.blogapi.service;

import com.ducminh.blogapi.dto.request.FollowRequest;
import com.ducminh.blogapi.dto.response.FollowResponse;
import com.ducminh.blogapi.entity.Follow;
import com.ducminh.blogapi.entity.FollowId;
import com.ducminh.blogapi.repository.FollowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FollowService {
    @Autowired
    private FollowRepository followRepository;

    public void create(FollowRequest request) {
        Follow follow = new Follow();
        follow.setId(new FollowId(request.getFollowingId(), request.getFollowerId()));
        followRepository.save(follow);
    }

}
