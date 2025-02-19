package com.ducminh.blogapi.config;

import com.ducminh.blogapi.constant.RoleName;
import com.ducminh.blogapi.entity.Role;
import com.ducminh.blogapi.entity.User;
import com.ducminh.blogapi.exception.AppException;
import com.ducminh.blogapi.constant.ErrorCode;
import com.ducminh.blogapi.repository.RoleRepository;
import com.ducminh.blogapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
@AllArgsConstructor
public class LearnApplicationConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final String userAdmin = "admin";
    private static final String passwordAdmin = "admin";

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            if (userRepository.findByUsername(userAdmin).isEmpty()) {
                Set<Role> roles = new HashSet<>();
                Role adminRole = roleRepository.findByName(RoleName.ADMIN.name())
                        .orElseThrow(() -> new AppException(ErrorCode.INVALID_ROLE));
                roles.add(adminRole);

                User admin = User.builder()
                        .username(userAdmin)
                        .password(passwordEncoder.encode(passwordAdmin))
                        .firstName("Admin")
                        .lastName("System")
                        .email("admin@system.com")
                        .roles(roles)
                        .build();
                userRepository.save(admin);
            }
        };
    }
}
