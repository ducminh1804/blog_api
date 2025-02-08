//package com.ducminh.blogapi.config;
//
//import com.ducminh.blogapi.entity.User;
//import com.ducminh.blogapi.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Configuration
//@EnableJpaAuditing
//public class AuditConfig {
//
//    @Bean
//    public AuditorAware<User> auditorProvider() {
//        return new AuditorAwareImpl();
//    }
//
//}
//
//@Component
//class AuditorAwareImpl implements AuditorAware<User> {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public Optional<User> getCurrentAuditor() {
////        String username = SecurityContextHolder.getContext().getAuthentication().getAuthorities().
//        Integer id = 1;
//        return userRepository.findById(id);
//    }
//}
