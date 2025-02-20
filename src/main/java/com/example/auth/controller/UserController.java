package com.example.auth.controller;

import com.example.auth.dto.UserDTO;
import com.example.auth.model.User;
import com.example.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO.getUsername(), userDTO.getPassword());
        return ResponseEntity.ok("User registered successfully!");
    }
}
