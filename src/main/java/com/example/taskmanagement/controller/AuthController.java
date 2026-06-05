package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.UserRegistrationDto;
import com.example.taskmanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDto dto) {
        String token = userService.register(dto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        String token = userService.login(username, password);
        return ResponseEntity.ok(token);
    }
}