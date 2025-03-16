package com.ducminh.blogapi.repository.jpa;

import com.ducminh.blogapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, String> {

    @Query(value = "Select * from posts where created_at < :createAt ORDER BY created_at DESC LIMIT 5", nativeQuery = true)
    List<Post> getPostsPagination(@Param("createAt") Instant createAt);

    List<Post> findByTitle(String title);
}
