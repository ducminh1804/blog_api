package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.CommentRequest;
import com.ducminh.blogapi.dto.request.PermissionRequest;
import com.ducminh.blogapi.dto.response.CommentResponse;
import com.ducminh.blogapi.dto.response.PermissionResponse;
import com.ducminh.blogapi.entity.Comment;
import com.ducminh.blogapi.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toComment(CommentRequest request);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    CommentResponse toCommentResponse(Comment comment);
}
