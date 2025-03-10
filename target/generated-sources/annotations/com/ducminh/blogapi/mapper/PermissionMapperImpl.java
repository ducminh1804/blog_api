package com.ducminh.blogapi.mapper;

import com.ducminh.blogapi.dto.request.PermissionRequest;
import com.ducminh.blogapi.dto.response.PermissionResponse;
import com.ducminh.blogapi.entity.Permission;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-10T16:30:04+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public Permission toPermission(PermissionRequest permissionRequest) {
        if ( permissionRequest == null ) {
            return null;
        }

        Permission permission = new Permission();

        permission.setName( permissionRequest.getName() );

        return permission;
    }

    @Override
    public PermissionResponse toPermissionResponse(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionResponse permissionResponse = new PermissionResponse();

        permissionResponse.setName( permission.getName() );

        return permissionResponse;
    }
}
