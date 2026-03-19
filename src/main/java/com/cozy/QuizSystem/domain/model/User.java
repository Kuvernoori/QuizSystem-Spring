package com.cozy.QuizSystem.domain.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class User {
    Long id;
    String phone;
    String role;
    String firstName;
    String lastName;
    String secondName;
    LocalDate birthDate;
    String email;
    String password;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
