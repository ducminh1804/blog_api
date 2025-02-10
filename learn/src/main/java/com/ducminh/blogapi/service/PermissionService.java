package com.ducminh.blogapi.service;

import com.ducminh.blogapi.dto.request.PermissionRequest;
import com.ducminh.blogapi.dto.request.UserCreationRequest;
import com.ducminh.blogapi.dto.response.PermissionResponse;
import com.ducminh.blogapi.entity.Permission;
import com.ducminh.blogapi.entity.User;
import com.ducminh.blogapi.exception.AppException;
import com.ducminh.blogapi.exception.ErrorCode;
import com.ducminh.blogapi.mapper.PermissionMapper;
import com.ducminh.blogapi.mapper.UserMapper;
import com.ducminh.blogapi.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest permissionRequest) {
        Permission permission = permissionRepository.save(permissionMapper.toPermission(permissionRequest));
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).collect(Collectors.toList());
    }

    public PermissionResponse delete(PermissionRequest permissionRequest) {
        Permission permission = permissionMapper.toPermission(permissionRequest);
        permission = permissionRepository.findById(permission.getId()).orElseThrow(() -> new AppException(ErrorCode.NOT_PERMISSION));
        permissionRepository.delete(permission);
        return permissionMapper.toPermissionResponse(permission);
    }
}
