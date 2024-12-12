package lesson.uz.config;

import lesson.uz.entity.UserEntity;
import lesson.uz.repository.UserRepository;
import lesson.uz.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisService redisService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /// Databazadan topish metodini Redisdan topish metodiga o'zgartirishim kerak.
//        Optional<UserEntity> optional = userRepository.findByPhoneAndVisibleTrue(username);
//        if (optional.isEmpty()) {
//            throw new UsernameNotFoundException(username);
//        }
//        UserEntity entity = optional.get();
        UserEntity entity = (UserEntity) redisService.findByUsername(username);
        CustomUserDetails userDetails = new CustomUserDetails(entity);
        return userDetails;
    }
}
