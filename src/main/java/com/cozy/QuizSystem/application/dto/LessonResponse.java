package com.cozy.QuizSystem.application.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonResponse {
    private Long id;
    private String title;
    private String content;
    private Integer orderIndex;
    private Long courseId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}