package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.RoleRequest;
import com.ducminh.blogapi.dto.response.RoleResponse;
import com.ducminh.blogapi.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "users", ignore = true)
    Role toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Role role);
}
