package com.cozy.QuizSystem.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class LoginRequest {
    @NotBlank (message = "Phone number is required")
    String phone;

    @NotBlank (message = "Password is required")
    String password;

}
