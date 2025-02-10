package com.ducminh.blogapi.service;

import com.ducminh.blogapi.dto.request.RoleRequest;
import com.ducminh.blogapi.dto.response.RoleResponse;
import com.ducminh.blogapi.entity.Permission;
import com.ducminh.blogapi.entity.Role;
import com.ducminh.blogapi.exception.AppException;
import com.ducminh.blogapi.exception.ErrorCode;
import com.ducminh.blogapi.mapper.RoleMapper;
import com.ducminh.blogapi.repository.PermissionRepository;
import com.ducminh.blogapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionRepository permissionRepository;

    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new AppException(ErrorCode.INVALID_ROLE));
    }

    // role lam parent cua permission. co role la phai co permission, co permission chua chac can co role
    public RoleResponse create(RoleRequest roleRequest) {
        Role role = roleMapper.toRole(roleRequest);
        List<Permission> permissions = permissionRepository.
                findAllByNameIn(new ArrayList<>(roleRequest.getPermissions()));
        System.out.println("permission: " + permissions);
        role.setPermissions(new HashSet<>(permissions));
        Role role1 = roleRepository.save(role);
        RoleResponse roleResponse = roleMapper.toRoleResponse(role1);
        return roleResponse;
    }

    public List<RoleResponse> getAll() {
        List<RoleResponse> roleResponses = roleRepository.findAll().stream().map(roleMapper::toRoleResponse).collect(Collectors.toList());
        return roleResponses;
    }
}
