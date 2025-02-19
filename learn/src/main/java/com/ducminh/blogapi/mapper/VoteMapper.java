package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.VoteRequest;
import com.ducminh.blogapi.dto.response.VoteResponse;
import com.ducminh.blogapi.entity.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VoteMapper {
    
    @Mapping(source = "postId", target = "voteId.postId")
    Vote toVote(@MappingTarget Vote vote, VoteRequest request);

    @Mapping(source = "voteId.postId", target = "postId")
    VoteResponse toVoteResponse(@MappingTarget VoteResponse response, Vote vote);

}
