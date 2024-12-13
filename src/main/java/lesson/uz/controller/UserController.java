package lesson.uz.controller;

import lesson.uz.dto.UserDTO;
import lesson.uz.dto.auth.AuthDTO;
import lesson.uz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("registration")
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.registration(dto));
    }

    @PostMapping("authorization")
    public ResponseEntity<UserDTO> authorization(@RequestBody AuthDTO dto) {
        UserDTO result = userService.authorization(dto);
        return ResponseEntity.ok(result);
    }

//    @PostMapping("/refresh-token")
//    public ResponseEntity<TokenDTO> refreshToken(@RequestBody TokenDTO dto){
//        TokenDTO result = userService.getNewAccessToken(dto);
//        return ResponseEntity.ok(result);
//    }
}
