package com.ducminh.blogapi.repository.jpa;

import com.ducminh.blogapi.dto.request.UserSubredditRequest;
import com.ducminh.blogapi.entity.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Subreddit, String> {
    @Query("select s from Subreddit s where s.id = :id")
    Optional<Subreddit> findSubredditById(@Param("id") String id);
}