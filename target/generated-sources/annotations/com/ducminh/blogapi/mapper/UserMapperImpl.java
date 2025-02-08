package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.UserCreationRequest;
import com.ducminh.blogapi.dto.request.UserUpdateRequest;
import com.ducminh.blogapi.dto.response.UserResponse;
import com.ducminh.blogapi.entity.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-08T22:27:24+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User converToUser(UserCreationRequest userCreationRequest) {
        if ( userCreationRequest == null ) {
            return null;
        }

        User user = new User();

        user.setFirstName( userCreationRequest.getFirstName() );
        user.setLastName( userCreationRequest.getLastName() );
        user.setUsername( userCreationRequest.getUsername() );
        user.setPassword( userCreationRequest.getPassword() );
        user.setEmail( userCreationRequest.getEmail() );

        return user;
    }

    @Override
    public User updateUser(User user, UserUpdateRequest userUpdateRequest) {
        if ( userUpdateRequest == null ) {
            return user;
        }

        user.setFirstName( userUpdateRequest.getFirstName() );
        user.setLastName( userUpdateRequest.getLastName() );
        user.setEmail( userUpdateRequest.getEmail() );

        return user;
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setFirstName( user.getFirstName() );
        userResponse.setLastName( user.getLastName() );
        userResponse.setUsername( user.getUsername() );
        userResponse.setEmail( user.getEmail() );

        return userResponse;
    }
}
