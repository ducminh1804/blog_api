package com.ducminh.blogapi.entity;

import com.ducminh.blogapi.constant.PrivacyEnum;
import com.ducminh.blogapi.entity.audit.UserAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "subreddits")
public class Subreddit extends UserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "privacy", nullable = false)
    private PrivacyEnum privacy;


    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "users_subreddits",
            joinColumns = @JoinColumn(name = "subreddit_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

//    @JsonIgnore
//    @ManyToMany(mappedBy = "subreddits")
//    private Set<User> users = new HashSet<>();
}
