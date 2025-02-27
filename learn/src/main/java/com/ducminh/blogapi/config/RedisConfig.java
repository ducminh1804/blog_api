package com.ducminh.blogapi.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
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

@EnableCaching
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
//day la vi cuu tinh : https://datmt.com/backend/fix-localdatetime-serialization-with-redis-spring-boot-cache/
        var jacksonSerializer = new GenericJackson2JsonRedisSerializer(objectMapper());
        var valueSerializer = RedisSerializationContext.SerializationPair.fromSerializer(jacksonSerializer);

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))  // TTL mặc định: 10 phút
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))//chuyen key thanh utf8
                .serializeValuesWith(valueSerializer)//chuyen value thanh json
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

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
                .configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
                .addModule(new JavaTimeModule())
                .findAndAddModules()
                .build();
    }

}
