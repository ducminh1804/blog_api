package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.RoleRequest;
import com.ducminh.blogapi.dto.request.SubredditRequest;
import com.ducminh.blogapi.dto.response.RoleResponse;
import com.ducminh.blogapi.dto.response.SubredditResponse;
import com.ducminh.blogapi.entity.Role;
import com.ducminh.blogapi.entity.Subreddit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
    Subreddit toSubreddit(SubredditRequest request);

    SubredditResponse toSubredditResponse(Subreddit subreddit);
}
