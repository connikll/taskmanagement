package com.example.taskmanagement.dto;

import com.example.taskmanagement.entity.TaskPriority;
import com.example.taskmanagement.entity.TaskStatus;
import lombok.Data;

@Data
public class TaskDto {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private String userId;
}