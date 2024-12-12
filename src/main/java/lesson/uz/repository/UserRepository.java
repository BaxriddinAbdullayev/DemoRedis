package lesson.uz.repository;

import lesson.uz.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, String> {

    Optional<UserEntity> findByPhoneAndVisibleTrue(String phone);
}
