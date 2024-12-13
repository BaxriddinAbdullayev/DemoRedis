package lesson.uz.service;

import io.jsonwebtoken.JwtException;
import lesson.uz.config.CustomUserDetails;
import lesson.uz.dto.JwtDTO;
import lesson.uz.dto.TokenDTO;
import lesson.uz.dto.UserDTO;
import lesson.uz.dto.auth.AuthDTO;
import lesson.uz.entity.UserEntity;
import lesson.uz.enums.GeneralStatus;
import lesson.uz.exp.AppBadRequestException;
import lesson.uz.repository.UserRepository;
import lesson.uz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisService redisService;

    public UserDTO registration(UserDTO dto) {
        Optional<UserEntity> optional = userRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if (optional.isPresent()) {
            return null;
        }

        UserEntity entity = new UserEntity();

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setRole(dto.getRole());

        userRepository.save(entity);
        redisService.save(entity.getPhone(), entity);

        dto.setId(entity.getId());
        return dto;
    }

    public UserDTO authorization(AuthDTO authDTO) {

        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authDTO.getPhone(),
                            authDTO.getPassword()
                    ));

            if (authenticate.isAuthenticated()) {
                CustomUserDetails user = (CustomUserDetails) authenticate.getPrincipal();

                UserDTO response = new UserDTO();
                response.setName(user.getName());
                response.setSurname(user.getSurname());
                response.setPhone(user.getUsername());
                response.setRole(user.getRole());
                response.setAccessToken(JwtUtil.encode(user.getUsername(), user.getRole().name()));
                response.setRefreshToken(JwtUtil.generateRefreshToken(user.getUsername(), user.getRole().name()));

                return response;
            }

            throw new AppBadRequestException("Invalid username or password");

        } catch (BadCredentialsException e) {
            throw new AppBadRequestException("Invalid username or password");
        }
    }

    public TokenDTO getNewAccessToken(TokenDTO dto){
        try{
            if(JwtUtil.isValid(dto.getRefreshToken())){
                JwtDTO jwtDTO = JwtUtil.decode(dto.getRefreshToken());

                Optional<UserEntity> optional = userRepository.findByPhoneAndVisibleTrue(jwtDTO.getUsername());

                if(optional.isPresent()){
                    UserEntity user = optional.get();

                    if(user.getStatus().equals(GeneralStatus.NOT_ACTIVE)){
                        throw new AppBadRequestException("Invalid token");
                    }

                    TokenDTO response =new TokenDTO();
                    response.setAccessToken(JwtUtil.encode(user.getPhone(),user.getRole().name()));
                    response.setRefreshToken(JwtUtil.generateRefreshToken(user.getPhone(),user.getRole().name()));
                    return response;
                }
            }
        }catch (JwtException e){

        }
        throw new AppBadRequestException("Invalid token");
    }
}
