package com.ducminh.blogapi.repository;

import com.ducminh.blogapi.dto.response.PostResponse;
import com.ducminh.blogapi.entity.Post;
import com.ducminh.blogapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, String> {

    @Query(value = "Select * from posts where created_at > :createAt ORDER BY created_at ASC LIMIT 10", nativeQuery = true)
    List<Post> getPostsPagination(@Param("createAt") Instant createAt);

}
