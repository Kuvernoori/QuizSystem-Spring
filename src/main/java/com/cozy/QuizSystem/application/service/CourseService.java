package com.cozy.QuizSystem.application.service;

import com.cozy.QuizSystem.application.dto.*;
import java.util.List;

public interface CourseService {
    List<CategoryResponse> getAllCategories();
    List<CourseResponse> getAllCourses();
    List<CourseResponse> getCoursesByCategory(Long categoryId);
    CourseResponse getCourseById(Long id);

    CourseResponse createCourse(String teacherPhone, CourseRequest request);
    CourseResponse updateCourse(Long id, String teacherPhone, CourseRequest request);
    void deleteCourse(Long id, String teacherPhone);

    LessonResponse addLesson(Long courseId, String teacherPhone, LessonRequest request);
    LessonResponse updateLesson(Long lessonId, String teacherPhone, LessonRequest request);
    void deleteLesson(Long lessonId, String teacherPhone);

    CategoryResponse createCategory(CategoryRequest request);
    void deleteCategory(Long id);
}