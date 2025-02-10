package com.ducminh.blogapi.dto.response;

import com.ducminh.blogapi.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private String name;
    private Set<PermissionResponse> permissions;
}
