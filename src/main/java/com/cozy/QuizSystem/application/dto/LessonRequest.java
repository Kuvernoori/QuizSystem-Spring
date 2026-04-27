package com.cozy.QuizSystem.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String content;

    @NotNull(message = "Order is required")
    private Integer orderIndex;
}