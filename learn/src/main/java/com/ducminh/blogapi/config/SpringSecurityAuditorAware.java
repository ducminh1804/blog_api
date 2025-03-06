package com.ducminh.blogapi.config;

import com.ducminh.blogapi.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component // Đảm bảo nó là một Spring Bean
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<String> user = (username != null) ? userRepository.findByUsername(username).of(username) : Optional.empty();
        return user;
    }
}
