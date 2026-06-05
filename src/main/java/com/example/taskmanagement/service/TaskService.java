package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.TaskDto;
import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskDto createTask(TaskDto dto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setUser(user);
        Task saved = taskRepository.save(task);
        return mapToDto(saved);
    }

    public List<TaskDto> getUserTasks(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return taskRepository.findByUserId(user.getId()).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TaskDto getTaskById(String id, String username) {
        Task task = taskRepository.findById(id).orElseThrow();
        if (!task.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        return mapToDto(task);
    }

    public TaskDto updateTask(String id, TaskDto dto, String username) {
        Task task = taskRepository.findById(id).orElseThrow();
        if (!task.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        Task saved = taskRepository.save(task);
        return mapToDto(saved);
    }

    public void deleteTask(String id, String username) {
        Task task = taskRepository.findById(id).orElseThrow();
        if (!task.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        taskRepository.delete(task);
    }

    private TaskDto mapToDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setUserId(task.getUser().getId());
        return dto;
    }
}