package com.ducminh.blogapi.service;

import com.ducminh.blogapi.dto.request.CommentRequest;
import com.ducminh.blogapi.dto.response.CommentResponse;
import com.ducminh.blogapi.mapper.CommentMapper;
import com.ducminh.blogapi.repository.CommentRepository;
import com.ducminh.blogapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public void createComment(CommentRequest request) {
        commentRepository.createComment(request);
    }

    @Cacheable("comment")
    public Page<CommentResponse> findByPostId(String postId, int parentId, Pageable pageable) {
        Page<CommentResponse> commentResponses = commentRepository.findByPostId(postId, parentId, pageable).map(commentMapper::toCommentResponse);
        return commentResponses;
    }
}
