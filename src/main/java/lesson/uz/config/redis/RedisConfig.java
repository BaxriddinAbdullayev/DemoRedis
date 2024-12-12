package lesson.uz.config.redis;

import lesson.uz.entity.UserEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisCommandFactory(){
            return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, UserEntity> redisTemplate(){
        RedisTemplate<String, UserEntity> template = new RedisTemplate<>();
        template.setConnectionFactory(redisCommandFactory());
        return template;
    }
}
