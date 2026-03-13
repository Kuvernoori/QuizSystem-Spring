package com.cozy.QuizSystem.api.controller;


import com.cozy.QuizSystem.application.dto.AuthResponse;
import com.cozy.QuizSystem.application.dto.LoginRequest;
import com.cozy.QuizSystem.application.dto.RegisterRequest;
import com.cozy.QuizSystem.application.dto.UserResponse;
import com.cozy.QuizSystem.application.service.AuthService;
import org.springframework.security.core.Authentication;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(201).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UserResponse> getMe(Authentication authentication) {
        String phone = authentication.getName();
        return ResponseEntity.ok(authService.getMe(phone));
    }
}
