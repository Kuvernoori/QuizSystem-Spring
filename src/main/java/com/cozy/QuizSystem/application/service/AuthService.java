package com.cozy.QuizSystem.application.service;

import com.cozy.QuizSystem.application.dto.AuthResponse;
import com.cozy.QuizSystem.application.dto.LoginRequest;
import com.cozy.QuizSystem.application.dto.RegisterRequest;
import com.cozy.QuizSystem.application.dto.UserResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    UserResponse getMe(String phone);
}
