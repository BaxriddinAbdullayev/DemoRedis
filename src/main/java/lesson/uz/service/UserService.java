package lesson.uz.service;

import lesson.uz.dto.UserDTO;
import lesson.uz.dto.auth.AuthDTO;
import lesson.uz.entity.UserEntity;
import lesson.uz.enums.GeneralStatus;
import lesson.uz.exp.AppBadRequestException;
import lesson.uz.repository.UserRepository;
import lesson.uz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public UserDTO authorization(AuthDTO authDTO){
        Optional<UserEntity> optional = userRepository.findByPhoneAndVisibleTrue(authDTO.getPhone());
        if (optional.isEmpty()){
            throw new AppBadRequestException("User not found");
        }

        UserEntity entity = optional.get();
        if(!bCryptPasswordEncoder.matches(authDTO.getPassword(),entity.getPassword())){
            throw new AppBadRequestException("User or Password wrong");
        }

        if(!entity.getStatus().equals(GeneralStatus.ACTIVE)){
            throw new AppBadRequestException("User not active");
        }

        UserDTO response = new UserDTO();
        response.setName(entity.getName());
        response.setSurname(entity.getSurname());
        response.setPassword(entity.getPassword());
        response.setRole(entity.getRole());
        response.setJwt(JwtUtil.encode(entity.getPhone(),entity.getRole().name()));

        return response;
    }
}
