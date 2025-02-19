package com.ducminh.blogapi.service;

import com.ducminh.blogapi.constant.ErrorCode;
import com.ducminh.blogapi.dto.request.VoteRequest;
import com.ducminh.blogapi.dto.response.VoteResponse;
import com.ducminh.blogapi.entity.Vote;
import com.ducminh.blogapi.exception.AppException;
import com.ducminh.blogapi.mapper.VoteMapper;
import com.ducminh.blogapi.repository.UserRepository;
import com.ducminh.blogapi.repository.VoteRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Slf4j
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoteMapper voteMapper;

    public VoteResponse updateVote(VoteRequest request, Principal principal) {
        Vote vote = new Vote();
        Vote vote1 = new Vote();
        VoteResponse voteAfter = new VoteResponse();
        String userId = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)).getId();

        int voteStatus = voteRepository.findByVoteId(request.getPostId(), userId).map(Vote::getVote).orElse(0);
        vote = voteMapper.toVote(vote, request);
        vote.getVoteId().setUserId(userId);
        if (voteStatus != request.getVote()) {
            vote1 = voteRepository.save(vote);
        }
        return voteMapper.toVoteResponse(voteAfter, vote1);
    }
}
