package lesson.uz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DemoRedisApplication {

    public static void main(String[] args) {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String encryptedKey = bc.encode("mazgi");
        System.out.println(encryptedKey);

        SpringApplication.run(DemoRedisApplication.class, args);
    }

}
