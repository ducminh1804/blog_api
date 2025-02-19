package com.ducminh.blogapi.repository;

import com.ducminh.blogapi.constant.VoteEnum;
import com.ducminh.blogapi.entity.Vote;
import com.ducminh.blogapi.entity.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, String> {
    @Query("select v from Vote v where v.voteId.userId = :userId and v.voteId.postId = :postId")
    Optional<Vote> findByVoteId(@Param("userId") String userId, @Param("postId") String postId);


}