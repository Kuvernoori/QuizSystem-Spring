package com.cozy.QuizSystem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class AuthResponse {
    String token;
    String role;
}
