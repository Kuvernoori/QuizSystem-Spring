package com.cozy.QuizSystem.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserResponse {
    Long id;
    String phone;
    String role;
    String firstName;
    String lastName;
    String secondName;
    LocalDate birthDate;
    String email;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}