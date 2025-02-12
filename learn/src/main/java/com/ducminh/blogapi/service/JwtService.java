package com.ducminh.blogapi.service;

import com.ducminh.blogapi.entity.InvalidToken;
import com.ducminh.blogapi.exception.AppException;
import com.ducminh.blogapi.exception.ErrorCode;
import com.ducminh.blogapi.mapper.RoleMapper;
import com.ducminh.blogapi.repository.InvalidTokenRepository;
import com.ducminh.blogapi.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtService {
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationInMs;
    @Value("${app.jwtRefreshExpirationInMs}")
    private long jwtRefreshExpirationInMs;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private InvalidTokenRepository invalidTokenRepository;


    public String generateToken(String username) {
        Date now = new Date();
        Date expired = new Date(System.currentTimeMillis() + jwtExpirationInMs);
        HashMap<String, List> claims = new HashMap<>();
        List<String> authorities = roleService.getAllRolePermissons(username);
        claims.put("authorities", authorities); // Đưa danh sách quyền vào token
//        String refreshToken = generateRefreshToken(username);
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .claim("authorities", authorities)
//                .claim("refresh_token", refreshToken)
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String extractUsername(String token) {
        if (token == null) {
            throw new AppException(ErrorCode.EMPTY_TOKEN);  // Ném ngoại lệ nếu token là null
        }
        String sub;
        try {
            sub = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException ex) {
            throw new AppException(ErrorCode.EXPIRED_JWT);
        }
        verify(token);
        return sub;
    }

    public String generateRefreshToken(String username) {
        Date now = new Date();
        Date expired = new Date(System.currentTimeMillis() + jwtRefreshExpirationInMs);
        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
        return refreshToken;
    }


    public Claims verifyRefreshToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            throw new AppException(ErrorCode.EXPIRED_JWT);
        } catch (Exception ex) {
            System.out.println(ex);
            throw new AppException(ErrorCode.INVALID_JWT);
        }
        return claims;
    }

    public String extractUsernameRefreshToken(String token) {
        if (token == null) throw new AppException(ErrorCode.EMPTY_TOKEN);
        String sub;
        try {
            sub = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException ex) {
            throw new AppException(ErrorCode.EXPIRED_JWT);
        }
        verifyRefreshToken(token);
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

    public boolean isValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername());
    }

    public Claims verify(String token) {
        Claims claimsJws = null;
        try {
            // Sử dụng parseClaimsJws thay vì parse để kiểm tra token
            claimsJws = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token).getBody(); // Kiểm tra chữ ký của token
        } catch (ExpiredJwtException ex) {
            throw new AppException(ErrorCode.EXPIRED_JWT);
        } catch (Exception ex) {
            System.out.println(ex);
            throw new AppException(ErrorCode.INVALID_JWT);
        }

        if (!invalidTokenRepository.findById(claimsJws.getId()).isEmpty())
            throw new AppException(ErrorCode.EXPIRED_JWT);
        return claimsJws;
    }
    //day chi la phuong thuc kiem tra riêng về token, chứ k kiểm tra quyển truy cập nên k bắt được lỗi
    //accessdined


    public void logout(String token) {
        try {
            Claims claims = verify(token);
            String jit = claims.getId();
            Date expired = claims.getExpiration();
            InvalidToken invalidToken = InvalidToken.builder()
                    .jit(jit)
                    .expiryTime(expired)
                    .build();
            invalidTokenRepository.save(invalidToken);
        } catch (AppException exception) {
            throw new AppException(ErrorCode.INVALID_JWT);
        }
    }


    public static void main(String[] args) {
        String key = "B85sVEdg188jJ5oWpqLaJuX4m/ILnbspdbGxkwU9928/zbKU+k4Wslf3K9UOJzwCuVMwcJTKhJyKYkgoz39CSw==";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9TVEFGRiIsIlJPTEVfVVNFUiIsIlJPTEVfQVBST1ZFX1BPU1QiLCJST0xFX0FERF9QT1NUIl0sImlhdCI6MTczOTIwMjMzMCwiZXhwIjoxNzM5Mjg4NzMwfQ.0JMtwQbLEQg_tUyh1ACL10-Gc4O2t-E_GfzLTo7feGY";

        Date now = new Date();
        Date expired = new Date(System.currentTimeMillis() + 8640000);
        System.out.println(Jwts.builder()
//                .setId(UUID.randomUUID().toString())
                .setSubject("user")
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact());
    }

}
