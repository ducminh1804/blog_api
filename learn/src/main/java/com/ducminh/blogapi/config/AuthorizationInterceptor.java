//package com.ducminh.blogapi.config;
//
//import com.ducminh.blogapi.service.JwtService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//
//import java.security.Principal;
//import java.util.List;
//
//@Component
//@Slf4j
//public class AuthorizationInterceptor implements ChannelInterceptor {
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//            String token = accessor.getFirstNativeHeader("Authorization");
//            if (token != null && token.startsWith("Bearer")) {
//                token = token.substring(7);
//                String username = jwtService.extractUsername(token);
//                List<SimpleGrantedAuthority> grantedAuthorities = jwtService.extractAuthority(token);
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                Principal authenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, userDetails.getPassword(), grantedAuthorities);
//                log.info("Principal: {}", authenticationToken.toString());
//                accessor.setUser(authenticationToken);
//                log.info("Accessor: {}", accessor.getUser().getName());
//            }
//        }
//        log.info("WebSocket preSend triggered for command: {}", accessor.getCommand());
//        return message;
//    }
//}
