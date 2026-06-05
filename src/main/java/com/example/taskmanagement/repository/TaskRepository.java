package com.example.taskmanagement.repository;

import com.example.taskmanagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByUserId(String userId);
    List<Task> findByUserIdAndStatus(String userId, String status);
}