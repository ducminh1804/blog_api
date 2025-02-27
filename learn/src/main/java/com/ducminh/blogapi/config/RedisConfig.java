package com.ducminh.blogapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);

        return new LettuceConnectionFactory(configuration);
    }

//    @Bean
    //day la cau hinh co ban
//    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//        return RedisCacheManager.create(connectionFactory);
//    }

    @Bean
//    day la cau hinh custom
    public RedisCacheConfiguration defaultCacheConfig() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))  // TTL mặc định: 10 phút
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))//chuyen key thanh utf8
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))//chuyen value thanh json
                .disableCachingNullValues(); // Không cache giá trị null
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // Tạo cấu hình riêng cho từng loại cache
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

//        cacheConfigurations.put("users", defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));  // Cache `users` sống 5 phút
//        cacheConfigurations.put("products", defaultCacheConfig().entryTtl(Duration.ofHours(1))); // Cache `products` sống 1 giờ

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig())      // Cấu hình mặc định: TTL 10 phút
                .withInitialCacheConfigurations(cacheConfigurations) // Áp dụng cấu hình riêng
                .transactionAware() // Hỗ trợ giao dịch (nếu cần)
                .build();
    }
}
