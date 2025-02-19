package com.ducminh.blogapi.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Value("${cloud.key}")
    private String cloudKey;
    @Value("${cloud.name}")
    private String cloudName;
    @Value("${cloud.secret}")
    private String cloudSecret;

    @Bean
    Cloudinary cloudinary() {
        String CLOUDINARY_URL = "cloudinary://" + cloudKey + ":" + cloudSecret + "@" + cloudName;
        Cloudinary cloudinary = new Cloudinary(CLOUDINARY_URL);
        return cloudinary;
    }
}
