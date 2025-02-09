package com.ducminh.blogapi.service;

import com.ducminh.blogapi.constant.RoleName;
import com.ducminh.blogapi.dto.request.UserCreationRequest;
import com.ducminh.blogapi.dto.request.UserUpdateRequest;
import com.ducminh.blogapi.dto.response.UserResponse;
import com.ducminh.blogapi.entity.Role;
import com.ducminh.blogapi.entity.User;
import com.ducminh.blogapi.exception.AppException;
import com.ducminh.blogapi.exception.ErrorCode;
import com.ducminh.blogapi.mapper.UserMapper;
import com.ducminh.blogapi.repository.RoleRepository;
import com.ducminh.blogapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    public User createUser(UserCreationRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) throw new AppException(ErrorCode.EMAIL_EXISTS);
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USERNAME_EXISTS);
        User user = Mappers.getMapper(UserMapper.class).converToUser(request);

        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        //set role
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(RoleName.USER.name()).orElseThrow(() -> new AppException(ErrorCode.INVALID_ROLE)));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public UserResponse findUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        UserResponse userResponse = Mappers.getMapper(UserMapper.class).toUserResponse(user);
        return userResponse;
    }

    public UserResponse userUpdate(String userId, UserUpdateRequest request) {
        User userOri = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
//        User user = Mappers.getMapper(UserMapper.class).updateUser(userOri, request);
        User userUpdate = Mappers.getMapper(UserMapper.class).updateUser(userOri, request);
        User userAfterUpdate = userRepository.save(userUpdate);
        UserResponse userResponse = Mappers.getMapper(UserMapper.class).toUserResponse(userAfterUpdate);
        return userResponse;
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }


}
