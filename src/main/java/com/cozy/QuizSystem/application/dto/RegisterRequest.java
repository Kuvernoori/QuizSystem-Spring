package com.cozy.QuizSystem.application.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class RegisterRequest {

    @NotBlank(message = "Phone is required")
    @Pattern(
            regexp = "^(\\+7|8|7|\\+8)[0-9]{10}$",
            message = "Invalid phone format. Example: +77001234567 or 87001234567"
    )
    String phone;

    @NotBlank(message = "Role is required")
    String role;

    @NotBlank(message = "First name is required")
    String firstName;

    String lastName;

    String secondName;

    LocalDate birthDate;

    @Email(message = "Invalid email format")
    String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 32, message = "Password must be between 6 and 32 characters")
    String password;


}
