package com.ducminh.blogapi.dto.response;

import com.ducminh.blogapi.entity.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostResponse implements Serializable {
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
