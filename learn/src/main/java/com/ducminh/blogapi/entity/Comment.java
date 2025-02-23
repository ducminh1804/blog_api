package com.ducminh.blogapi.entity;

import com.ducminh.blogapi.entity.audit.DateAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "parent_id")
    private int parentId;

    @Column(name = "content")
    private String content;

    @Column(name = "vote_up")
    private int voteUp;

    @Column(name = "vote_down")
    private int voteDown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "accestor", cascade = CascadeType.ALL)
    private Set<CommentClosure> accestors = new HashSet<>();

    @OneToMany(mappedBy = "descendant", cascade = CascadeType.ALL)
    private Set<CommentClosure> descendant = new HashSet<>();

}
