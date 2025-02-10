package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.PermissionRequest;
import com.ducminh.blogapi.dto.response.PermissionResponse;
import com.ducminh.blogapi.entity.Permission;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);
}
