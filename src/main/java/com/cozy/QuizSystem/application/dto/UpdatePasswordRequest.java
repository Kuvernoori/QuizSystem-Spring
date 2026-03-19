package com.cozy.QuizSystem.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UpdatePasswordRequest {

    @NotBlank(message = "Old password is required")
    String oldPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 6, max = 32, message = ("Password must be between 6 and 32 characters"))
    String newPassword;

}
