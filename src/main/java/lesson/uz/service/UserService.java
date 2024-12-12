package lesson.uz.service;

import lesson.uz.dto.UserDTO;
import lesson.uz.entity.UserEntity;
import lesson.uz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RedisService redisService;

    public UserDTO registration(UserDTO dto){
        Optional<UserEntity> optional = userRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if(optional.isPresent()){
            return null;
        }

        UserEntity entity = new UserEntity();

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setRole(dto.getRole());

        userRepository.save(entity);
        redisService.save(entity.getPhone(),entity);

        dto.setId(entity.getId());
        return dto;
    }
}
