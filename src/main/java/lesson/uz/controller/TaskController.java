package lesson.uz.controller;

import lesson.uz.config.CustomUserDetails;
import lesson.uz.dto.TaskDTO;
import lesson.uz.service.TaskService;
import lesson.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("")
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO dto) {
        TaskDTO result = taskService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<List<TaskDTO>> getAll() {
        List<TaskDTO> result = taskService.getAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/my")
    public ResponseEntity<List<TaskDTO>> getMyTasks() {
        List<TaskDTO> result = taskService.getCurrentUserTasks();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getById(@PathVariable String id) {
        TaskDTO result = taskService.getById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@RequestBody TaskDTO student,
                                          @PathVariable("id") String id) {
        Boolean result = taskService.update(student, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteAsAdmin(@PathVariable("id") String id) {
        taskService.deleteAsAdmin(id);
        return ResponseEntity.ok().build();
    }
}