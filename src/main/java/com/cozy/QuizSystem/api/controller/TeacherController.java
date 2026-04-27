package com.cozy.QuizSystem.api.controller;

import com.cozy.QuizSystem.application.dto.*;
import com.cozy.QuizSystem.application.service.CourseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
@RequiredArgsConstructor
public class TeacherController {

    private final CourseService courseService;

    @PostMapping("/courses")
    public ResponseEntity<CourseResponse> createCourse(
            @AuthenticationPrincipal String phone,
            @Valid @RequestBody CourseRequest request) {
        return ResponseEntity.status(201).body(courseService.createCourse(phone, request));
    }

    @PatchMapping("/courses/{id}")
    public ResponseEntity<CourseResponse> updateCourse(
            @PathVariable Long id,
            @AuthenticationPrincipal String phone,
            @Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.updateCourse(id, phone, request));
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(
            @PathVariable Long id,
            @AuthenticationPrincipal String phone) {
        courseService.deleteCourse(id, phone);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/courses/{courseId}/lessons")
    public ResponseEntity<LessonResponse> addLesson(
            @PathVariable Long courseId,
            @AuthenticationPrincipal String phone,
            @Valid @RequestBody LessonRequest request) {
        return ResponseEntity.status(201).body(courseService.addLesson(courseId, phone, request));
    }

    @PatchMapping("/lessons/{lessonId}")
    public ResponseEntity<LessonResponse> updateLesson(
            @PathVariable Long lessonId,
            @AuthenticationPrincipal String phone,
            @Valid @RequestBody LessonRequest request) {
        return ResponseEntity.ok(courseService.updateLesson(lessonId, phone, request));
    }

    @DeleteMapping("/lessons/{lessonId}")
    public ResponseEntity<Void> deleteLesson(
            @PathVariable Long lessonId,
            @AuthenticationPrincipal String phone) {
        courseService.deleteLesson(lessonId, phone);
        return ResponseEntity.noContent().build();
    }
}