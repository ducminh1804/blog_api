package com.ducminh.blogapi.config;

import com.ducminh.blogapi.repository.jpa.UserRepository;
import com.ducminh.blogapi.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;
import java.util.List;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") //
                .withSockJS();

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String token = accessor.getFirstNativeHeader("Authorization");
                    if (token != null && token.startsWith("Bearer")) {
                        token = token.substring(7);
                        String username = jwtService.extractUsername(token);
                        List<SimpleGrantedAuthority> grantedAuthorities = jwtService.extractAuthority(token);
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        String userId = userRepository.findByUsername(username).get().getId();
                        Principal authenticationToken = new StompPrincipal(userId);
                        log.info("Principal: {}", authenticationToken.toString());
                        accessor.setUser(authenticationToken);
                        log.info("Accessor: {}", accessor.getUser().getName());
                    }
                }
                log.info("WebSocket preSend triggered for command: {}", accessor.getCommand());
                return message;
            }
        });
    }

}


//
//        🚀 Bước 1: Xác định vấn đề
//        WebSocket không hỗ trợ header HTTP trong handshake ban đầu.
//        Nếu không dùng session-based authentication (cookies), ta phải tìm cách gửi token khi client kết nối.
//        STOMP có hỗ trợ header trong CONNECT request, vậy có thể truyền token qua đây.
//        🚀 Bước 2: Chọn vị trí xử lý token
//        Vì token nằm trong header STOMP, ta cần chặn CONNECT request để xác thực trước khi cho phép WebSocket kết nối.
//        Interceptor (ChannelInterceptor) là nơi phù hợp để làm việc này.
//        🚀 Bước 3: Viết ý tưởng bằng ngôn ngữ tự nhiên
//        Mình cần làm gì trong preSend?
//
//        ✔ Lấy tin nhắn STOMP (CONNECT request)
//        ✔ Lấy token từ header "Authorization"
//        ✔ Xác thực token bằng JwtTokenProvider
//        ✔ Nếu hợp lệ → Gán Authentication vào session
//        ✔ Nếu không hợp lệ → Chặn kết nối
//
//        💡 Khi đã có tư duy này, việc viết code chỉ còn là dịch ý tưởng sang Java!
// **** jwtfilter se chay truoc o moi http =>> resend se chay khi co yeu cau connect, tuc la van cahay sau jwtFilter
