package com.cozy.QuizSystem.domain.repository;

import com.cozy.QuizSystem.domain.model.Category;
import com.cozy.QuizSystem.domain.model.Course;
import com.cozy.QuizSystem.domain.model.Lesson;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    List<Course> findAll();
    List<Course> findByCategoryId(Long categoryId);
    List<Course> findByTeacherId(Long teacherId);
    Optional<Course> findById(Long id);
    Course save(Course course);
    Course update(Course course);
    void deleteById(Long id);
}
