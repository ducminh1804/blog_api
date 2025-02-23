package com.ducminh.blogapi.repository;

import com.ducminh.blogapi.dto.request.CommentRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

public class CustomCommentRepositoryImpl implements CustomCommentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void createComment(CommentRequest request) {
        entityManager.createNativeQuery("""
                            INSERT INTO comments (parent_id, user_id, post_id, content) 
                            VALUES (:parentId, :userId, :postId, :content)
                        """).setParameter("parentId", request.getParentId())
                .setParameter("userId", request.getUserId())
                .setParameter("postId", request.getPostId())
                .setParameter("content", request.getContent())
                .executeUpdate();

        Long lastInsertId = (Long) entityManager.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult();
        entityManager.createNativeQuery("""
                            INSERT INTO comments_closure (ancestor_id, descendant_id, depth) 
                            VALUES (:id, :id, 0)
                        """).setParameter("id", lastInsertId)
                .executeUpdate();

        Long lastInsertIdClosure = (Long) entityManager.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult();

        entityManager.createNativeQuery("""
                            INSERT INTO comments_closure (ancestor_id, descendant_id, depth) 
                            SELECT ancestor_id, :id, depth + 1 
                            FROM comments_closure WHERE descendant_id = :parentId
                        """).setParameter("id", lastInsertIdClosure)
                .setParameter("parentId", request.getParentId())
                .executeUpdate();
    }
}
