package com.ducminh.blogapi.dto.response;

import com.ducminh.blogapi.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private String id;
    private String title;
    private String body;
    private Set<Tag> tags;
    private String username;
    private String kind;
    private Instant createdAt;
    private int upVoted;
    private int downVoted;
}
