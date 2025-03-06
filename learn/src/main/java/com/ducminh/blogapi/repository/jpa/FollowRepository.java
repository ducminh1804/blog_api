package com.ducminh.blogapi.repository.jpa;

import com.ducminh.blogapi.dto.response.FollowResponse;
import com.ducminh.blogapi.dto.response.FollowResponseProjection;
import com.ducminh.blogapi.entity.Follow;
import com.ducminh.blogapi.entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {
    @Query(value = "SELECT f.following_id, u.username FROM follows f JOIN users u ON f.following_id = u.id WHERE f.follower_id = :id", nativeQuery = true)
    Optional<List<FollowResponseProjection>> findAllFollowerById(@Param("id") String id);

}
