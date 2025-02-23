package com.ducminh.blogapi.repository;

import com.ducminh.blogapi.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CommentRepository extends JpaRepository<Comment, Integer>, CustomCommentRepository {
    @Query(value = "SELECT * FROM comments WHERE post_id = :postId AND parent_id = :parentId",
            nativeQuery = true)
    Page<Comment> findByPostId(@Param("postId") String postId,
                               @Param("parentId") Integer parentId,
                               Pageable pageable);

}