package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.UserRegistrationDto;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.repository.UserRepository;
import com.example.taskmanagement.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(UserRegistrationDto dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setRoles(new HashSet<>(java.util.List.of("ROLE_USER")));
        userRepository.save(user);
        return jwtUtil.generateToken(user.getUsername());
    }

    public String login(String username, String password) {
        // Simplified, in real would use AuthenticationManager
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return jwtUtil.generateToken(username);
        }
        throw new RuntimeException("Invalid credentials");
    }
}