package com.ducminh.blogapi.repository.jpa;

import com.ducminh.blogapi.entity.Message;
import com.ducminh.blogapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    @Query(value = "Select * from messages " +
            "where created_at < :createAt " +
            "and ((sender_id = :senderId and recipent_id = :recipentId) " +
            "or (sender_id = :recipentId and recipent_id = :senderId))" +
            " ORDER BY created_at DESC LIMIT 5", nativeQuery = true)
    List<Message> getMessagesPagination(@Param("senderId") String senderId,
                                        @Param("recipentId") String recipentId,
                                        @Param("createAt") Instant createAt);
}