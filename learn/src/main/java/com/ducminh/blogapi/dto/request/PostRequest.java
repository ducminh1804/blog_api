package com.ducminh.blogapi.dto.request;

import com.ducminh.blogapi.entity.Comment;
import com.ducminh.blogapi.entity.Tag;
import com.ducminh.blogapi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private String title;
    private String body;
    private Set<String> tags;
    private String kind;
}
