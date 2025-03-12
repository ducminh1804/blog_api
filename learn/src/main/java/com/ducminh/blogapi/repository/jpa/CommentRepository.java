package com.ducminh.blogapi.repository.jpa;

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

    @Query(value = "SELECT EXISTS (SELECT 1 FROM comments_closure WHERE ancestor_id = :ancestorId and depth = 1)", nativeQuery = true)
    Long checkChildComment(@Param("ancestorId") String ancestorId);
}