package io.orbion.url_shortner_be.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.orbion.url_shortner_be.entity.model.User;
import io.orbion.url_shortner_be.entity.request.LoginRequest;
import io.orbion.url_shortner_be.entity.request.RegisterRequest;
import io.orbion.url_shortner_be.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/public/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .role("ROLE_USER")
                .build();

        userService.register(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/public/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
}
