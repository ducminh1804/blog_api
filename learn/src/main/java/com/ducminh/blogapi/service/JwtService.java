package com.ducminh.blogapi.service;

import com.ducminh.blogapi.dto.response.RoleResponse;
import com.ducminh.blogapi.entity.Role;
import com.ducminh.blogapi.entity.User;
import com.ducminh.blogapi.exception.AppException;
import com.ducminh.blogapi.exception.ErrorCode;
import com.ducminh.blogapi.mapper.RoleMapper;
import com.ducminh.blogapi.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleService roleService;

    public String generateToken(String username) {
        Date now = new Date();
        Date expired = new Date(System.currentTimeMillis() + jwtExpirationInMs);
        HashMap<String, List> claims = new HashMap<>();
        List<String> authorities = roleService.getAllRolePermissons(username);
        claims.put("authorities", authorities); // Đưa danh sách quyền vào token

        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities)
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String extractUsername(String token) {
        String sub;
        if (token == null) {
            throw new AppException(ErrorCode.INVALID_JWT);  // Ném ngoại lệ nếu token là null
        }
        sub = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return sub;
    }

    public List<SimpleGrantedAuthority> extractAuthority(String token) {
        if (token == null) throw new AppException(ErrorCode.INVALID_JWT);
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        Claims authority = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        List<String> roles = authority.get("authorities", ArrayList.class);
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    public boolean verify(String token) {
        try {
            // Sử dụng parseClaimsJws thay vì parse để kiểm tra token
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token); // Kiểm tra chữ ký của token
            return true;
        } catch (ExpiredJwtException ex) {
            throw new AppException(ErrorCode.EXPIRED_JWT);
        } catch (Exception ex) {
            System.out.println(ex);
            throw new AppException(ErrorCode.INVALID_JWT);  // Sửa chính tả ErrorCode.IVALID_JWT => ErrorCode.INVALID_JWT
        }
    }
    //day chi la phuong thuc kiem tra riêng về token, chứ k kiểm tra quyển truy cập nên k bắt được lỗi
    //accessdined

    public boolean isValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername());
    }

    public static void main(String[] args) {
        String key = "B85sVEdg188jJ5oWpqLaJuX4m/ILnbspdbGxkwU9928/zbKU+k4Wslf3K9UOJzwCuVMwcJTKhJyKYkgoz39CSw==";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9TVEFGRiIsIlJPTEVfVVNFUiIsIlJPTEVfQVBST1ZFX1BPU1QiLCJST0xFX0FERF9QT1NUIl0sImlhdCI6MTczOTIwMjMzMCwiZXhwIjoxNzM5Mjg4NzMwfQ.0JMtwQbLEQg_tUyh1ACL10-Gc4O2t-E_GfzLTo7feGY";

        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        Claims authority = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        System.out.println(authority.get("authorities", ArrayList.class));
    }

}
