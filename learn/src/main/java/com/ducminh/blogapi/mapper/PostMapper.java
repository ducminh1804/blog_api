package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.PostRequest;
import com.ducminh.blogapi.dto.response.PostResponse;
import com.ducminh.blogapi.entity.Post;
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
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "username", ignore = true)
    PostResponse toPostResponse(Post post);

}
