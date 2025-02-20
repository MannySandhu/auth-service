package com.example.auth.service;

import com.example.auth.model.User;
import com.example.auth.model.Role;
import com.example.auth.repository.UserRepository;
import com.example.auth.utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

    public String registerUser(String username, String password) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            return "User already exists!";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Securely encode password
        user.setRole(Role.USER); // Default role
        userRepository.save(user);

        return "User registered successfully!";
    }

    public String authenticateUser(String username, String password) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            return "Invalid credentials!";
        }

        return jwtUtil.generateToken(user.get().getUsername()); // Generate and return a JWT
    }
}
