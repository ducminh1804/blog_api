package com.ducminh.blogapi.config;

import com.ducminh.blogapi.entity.User;
import com.ducminh.blogapi.exception.AppException;
import com.ducminh.blogapi.constant.ErrorCode;
import com.ducminh.blogapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

@Configuration
@Slf4j
public class ApplicationConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    @Transactional
    public UserDetailsService userDetailsService() {
        return username -> new UserDetails() {
            User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

            //            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//                List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
//                List<SimpleGrantedAuthority> roles = user.getRoles().stream()
//                        .map(role -> new SimpleGrantedAuthority(role.getName()))
//                        .collect(Collectors.toList());
//
//                List<SimpleGrantedAuthority> permissions = user.getRoles().stream()
//                        .flatMap(role -> role.getPermissions().stream())
//                        .map(permission -> new SimpleGrantedAuthority(permission.getName()))
//                        .collect(Collectors.toList());
//
//                grantedAuthorities.addAll(roles);
//                grantedAuthorities.addAll(permissions);
//                return grantedAuthorities;
//            }
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }
        };
    }

    @Bean//authenticationProvider: tim va nap du lieu user,ma hoa mat khau
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
            Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
