package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.CommentRequest;
import com.ducminh.blogapi.dto.request.MessageRequest;
import com.ducminh.blogapi.dto.response.CommentResponse;
import com.ducminh.blogapi.dto.response.MessageResponse;
import com.ducminh.blogapi.entity.Comment;
import com.ducminh.blogapi.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(source = "recipentId", target = "recipentId")
    Message toMessage(MessageRequest request);

    @Mapping(source = "user.id", target = "senderId")
    MessageResponse toMessageResponse(Message message);
}
