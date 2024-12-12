package lesson.uz.controller;

import lesson.uz.config.CustomUserDetails;
import lesson.uz.dto.TaskDTO;
import lesson.uz.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        System.out.println(principal.getName());


        TaskDTO result = taskService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<List<TaskDTO>> getAll() {
        List<TaskDTO> result = taskService.getAll();
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
}