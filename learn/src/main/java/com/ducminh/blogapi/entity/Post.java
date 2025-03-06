package com.ducminh.blogapi.entity;

import com.ducminh.blogapi.entity.audit.UserAudit;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
@Builder
@Getter
@Setter
public class Post extends UserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "kind")
    private String kind;

    @Column(name = "vote_up")
    private int upVoted;

    @Column(name = "vote_down")
    private int downVoted;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", kind='" + kind + '\'' +
                ", upVoted=" + upVoted +
                ", downVoted=" + downVoted +
                ", comments=" + comments +
                ", user=" + user +
                ", tags=" + tags +
                '}';
    }
}