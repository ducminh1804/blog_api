package com.ducminh.blogapi.repository.jpa;

import com.ducminh.blogapi.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, String> {
}