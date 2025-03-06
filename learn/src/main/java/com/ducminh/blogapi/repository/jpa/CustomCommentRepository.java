package com.ducminh.blogapi.repository.jpa;

import com.ducminh.blogapi.dto.request.CommentRequest;
import com.ducminh.blogapi.dto.response.CommentResponse;

import java.util.List;

public interface CustomCommentRepository {
    void createComment(CommentRequest request);


}
