package com.cozy.QuizSystem.application.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private Long categoryId;
    private String categoryName;
    private Long teacherId;
    private String teacherName;
    private String level;
    private int lessonCount;
    private List<LessonResponse> lessons;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}