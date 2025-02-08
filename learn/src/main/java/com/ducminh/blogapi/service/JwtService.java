package com.ducminh.blogapi.service;

import com.ducminh.blogapi.exception.AppException;
import com.ducminh.blogapi.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;


    public String generateToken(String username) {
        Date now = new Date();
        Date expired = new Date(System.currentTimeMillis() + jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(username)
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
        } catch (SignatureException ex) {
            throw new AppException(ErrorCode.INVALID_SIGNATURE);
        } catch (Exception ex) {
            System.out.println(ex);
            throw new AppException(ErrorCode.INVALID_JWT);  // Sửa chính tả ErrorCode.IVALID_JWT => ErrorCode.INVALID_JWT
        }
    }

    public boolean isValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername());
    }

    public static void main(String[] args) {
        String key = "B85sVEdg188jJ5oWpqLaJuX4m/ILnbspdbGxkwU9928/zbKU+k4Wslf3K9UOJzwCuVMwcJTKhJyKYkgoz39CSw==";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2ZGZvb29vIiwiaWF0IjoxNzM4OTQwMTgyLCJleHAiOjE3MzkwMjY1ODJ9.hB9wycxhmp9h5LfQDGp-V53Gk5NzEuCpa_AuUxlBm-s";
        System.out.println(Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody().getSubject());
    }

}
