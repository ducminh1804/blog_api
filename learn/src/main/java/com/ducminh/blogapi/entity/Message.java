package com.ducminh.blogapi.entity;

import com.ducminh.blogapi.entity.audit.DateAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Entity
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "recipent_id")
    private String recipentId;

    @Column(name = "content")
    private String content;

    @Column(name = "createdAt")
    private Instant createdAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User user;
}
