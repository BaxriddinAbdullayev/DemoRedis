package lesson.uz.repository;

import lesson.uz.entity.TaskEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<TaskEntity, String> {

    List<TaskEntity> findByUserId(String userId);

}
