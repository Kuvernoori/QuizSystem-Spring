package com.cozy.QuizSystem.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Lesson {
    private Long id;
    private String title;
    private String content;
    private Integer orderIndex;
    private Long courseId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
