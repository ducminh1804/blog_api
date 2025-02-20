package com.ducminh.blogapi.repository;

import com.ducminh.blogapi.entity.Follow;
import com.ducminh.blogapi.entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {
}