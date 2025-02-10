package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.UserCreationRequest;
import com.ducminh.blogapi.dto.request.UserUpdateRequest;
import com.ducminh.blogapi.dto.response.UserResponse;
import com.ducminh.blogapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper
public interface UserMapper {
    //    dat ten field giong nhau
    User converToUser(UserCreationRequest userCreationRequest);

    //    cap nhat it field => nhieu field
    @Mapping(source = "userUpdateRequest.firstName", target = "firstName")
    @Mapping(source = "userUpdateRequest.lastName", target = "lastName")
    @Mapping(source = "userUpdateRequest.email", target = "email")
    User updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);

    UserResponse toUserResponse(User user);
}
