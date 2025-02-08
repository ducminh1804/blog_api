package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.dto.response.ApiResponse;
import com.ducminh.blogapi.dto.request.UserCreationRequest;
import com.ducminh.blogapi.dto.request.UserUpdateRequest;
import com.ducminh.blogapi.dto.response.UserResponse;
import com.ducminh.blogapi.entity.User;
import com.ducminh.blogapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    User createUser(@RequestBody @Valid UserCreationRequest request) {
        return userService.createUser(request);
    }

    @GetMapping
    List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable("userId") String userId) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(userService.findUserById(userId));
        return apiResponse;
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable("userId") String userId, @RequestBody @Valid UserUpdateRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        UserResponse userResponse = userService.userUpdate(userId, request);
        apiResponse.setData(userResponse);
        return apiResponse;
    }

    @DeleteMapping("/{userId}")
    void deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
    }
}
