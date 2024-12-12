package lesson.uz.service;

import lesson.uz.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, UserEntity> redisTemplate;

    public void save(String username, UserEntity entity) {
        redisTemplate.opsForValue().set(username, entity);
    }

    public UserEntity findByUsername(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    public void delete(String username) {
        redisTemplate.delete(username);
    }
}
