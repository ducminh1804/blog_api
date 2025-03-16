package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.PostRequest;
import com.ducminh.blogapi.dto.response.PostResponse;
import com.ducminh.blogapi.entity.Post;
import com.ducminh.blogapi.entity.PostEs;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "tags", ignore = true)
    Post toPost(PostRequest postRequest);

    @Mapping(target = "id", source = "id")
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "username", ignore = true)
    @Mapping(source = "user.username", target = "username")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    PostResponse toPostResponse(Post post);

    PostEs toPostEs(PostResponse response);
}
