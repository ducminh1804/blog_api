package com.ducminh.blogapi.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StompPrincipal implements Principal {
    private String userId;

    @Override
    public String getName() {
        return userId;
    }
}
