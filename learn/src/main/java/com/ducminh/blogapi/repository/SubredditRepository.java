package com.ducminh.blogapi.repository;

import com.ducminh.blogapi.entity.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubredditRepository extends JpaRepository<Subreddit, String> {
}