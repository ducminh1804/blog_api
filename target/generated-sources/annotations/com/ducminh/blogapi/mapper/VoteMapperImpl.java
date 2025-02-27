package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.VoteRequest;
import com.ducminh.blogapi.dto.response.VoteResponse;
import com.ducminh.blogapi.entity.Vote;
import com.ducminh.blogapi.entity.VoteId;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-27T22:16:23+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class VoteMapperImpl implements VoteMapper {

    @Override
    public Vote toVote(Vote vote, VoteRequest request) {
        if ( request == null ) {
            return vote;
        }

        if ( vote.getVoteId() == null ) {
            vote.setVoteId( new VoteId() );
        }
        voteRequestToVoteId( request, vote.getVoteId() );
        vote.setVote( request.getVote() );

        return vote;
    }

    @Override
    public VoteResponse toVoteResponse(VoteResponse response, Vote vote) {
        if ( vote == null ) {
            return response;
        }

        response.setPostId( voteVoteIdPostId( vote ) );
        response.setVote( vote.getVote() );

        return response;
    }

    protected void voteRequestToVoteId(VoteRequest voteRequest, VoteId mappingTarget) {
        if ( voteRequest == null ) {
            return;
        }

        mappingTarget.setPostId( voteRequest.getPostId() );
    }

    private String voteVoteIdPostId(Vote vote) {
        if ( vote == null ) {
            return null;
        }
        VoteId voteId = vote.getVoteId();
        if ( voteId == null ) {
            return null;
        }
        String postId = voteId.getPostId();
        if ( postId == null ) {
            return null;
        }
        return postId;
    }
}
