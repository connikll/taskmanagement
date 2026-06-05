package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.TaskDto;
import com.example.taskmanagement.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto, Authentication authentication) {
        String username = authentication.getName();
        TaskDto created = taskService.createTask(taskDto, username);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks(Authentication authentication) {
        String username = authentication.getName();
        List<TaskDto> tasks = taskService.getUserTasks(username);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable String id, Authentication authentication) {
        String username = authentication.getName();
        TaskDto task = taskService.getTaskById(id, username);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable String id, @RequestBody TaskDto taskDto, Authentication authentication) {
        String username = authentication.getName();
        TaskDto updated = taskService.updateTask(id, taskDto, username);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id, Authentication authentication) {
        String username = authentication.getName();
        taskService.deleteTask(id, username);
        return ResponseEntity.noContent().build();
    }
}