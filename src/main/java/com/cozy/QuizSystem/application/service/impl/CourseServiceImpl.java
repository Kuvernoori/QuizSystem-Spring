package com.cozy.QuizSystem.application.service.impl;

import com.cozy.QuizSystem.application.dto.*;
import com.cozy.QuizSystem.application.service.CourseService;
import com.cozy.QuizSystem.domain.model.*;
import com.cozy.QuizSystem.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category already exists");
        }
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::toCourseResponse)
                .toList();
    }

    @Override
    public List<CourseResponse> getCoursesByCategory(Long categoryId) {
        return courseRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::toCourseResponse)
                .toList();
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return toCourseResponse(course);
    }

    @Override
    public CourseResponse createCourse(String teacherPhone, CourseRequest request) {
        User teacher = userRepository.findByPhone(teacherPhone)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        String level = request.getLevel().toUpperCase();
        if (!level.equals("BEGINNER") && !level.equals("INTERMEDIATE") && !level.equals("ADVANCED")) {
            throw new RuntimeException("Invalid level. Must be BEGINNER, INTERMEDIATE or ADVANCED");
        }

        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .categoryId(request.getCategoryId())
                .teacherId(teacher.getId())
                .level(level)
                .build();

        return toCourseResponse(courseRepository.save(course));
    }

    @Override
    public CourseResponse updateCourse(Long id, String teacherPhone, CourseRequest request) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        User teacher = userRepository.findByPhone(teacherPhone)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        if (!existing.getTeacherId().equals(teacher.getId())) {
            throw new RuntimeException("You can only edit your own courses");
        }

        Course updated = Course.builder()
                .id(existing.getId())
                .title(request.getTitle() != null ? request.getTitle() : existing.getTitle())
                .description(request.getDescription() != null ? request.getDescription() : existing.getDescription())
                .categoryId(request.getCategoryId() != null ? request.getCategoryId() : existing.getCategoryId())
                .teacherId(existing.getTeacherId())
                .level(request.getLevel() != null ? request.getLevel().toUpperCase() : existing.getLevel())
                .build();

        return toCourseResponse(courseRepository.update(updated));
    }

    @Override
    public void deleteCourse(Long id, String teacherPhone) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        User teacher = userRepository.findByPhone(teacherPhone)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        if (!course.getTeacherId().equals(teacher.getId())) {
            throw new RuntimeException("You can only delete your own courses");
        }

        courseRepository.deleteById(id);
    }


    @Override
    public LessonResponse addLesson(Long courseId, String teacherPhone, LessonRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        User teacher = userRepository.findByPhone(teacherPhone)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        if (!course.getTeacherId().equals(teacher.getId())) {
            throw new RuntimeException("You can only add lessons to your own courses");
        }

        Lesson lesson = Lesson.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .orderIndex(request.getOrderIndex())
                .courseId(courseId)
                .build();

        return toLessonResponse(lessonRepository.save(lesson));
    }

    @Override
    public LessonResponse updateLesson(Long lessonId, String teacherPhone, LessonRequest request) {
        Lesson existing = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        Course course = courseRepository.findById(existing.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        User teacher = userRepository.findByPhone(teacherPhone)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        if (!course.getTeacherId().equals(teacher.getId())) {
            throw new RuntimeException("You can only edit lessons of your own courses");
        }

        Lesson updated = Lesson.builder()
                .id(existing.getId())
                .title(request.getTitle() != null ? request.getTitle() : existing.getTitle())
                .content(request.getContent() != null ? request.getContent() : existing.getContent())
                .orderIndex(request.getOrderIndex() != null ? request.getOrderIndex() : existing.getOrderIndex())
                .courseId(existing.getCourseId())
                .build();

        return toLessonResponse(lessonRepository.update(updated));
    }

    @Override
    public void deleteLesson(Long lessonId, String teacherPhone) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        Course course = courseRepository.findById(lesson.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        User teacher = userRepository.findByPhone(teacherPhone)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        if (!course.getTeacherId().equals(teacher.getId())) {
            throw new RuntimeException("You can only delete lessons of your own courses");
        }

        lessonRepository.deleteById(lessonId);
    }


    private CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    private CourseResponse toCourseResponse(Course course) {

        String teacherName = userRepository.findById(course.getTeacherId())
                .map(u -> u.getFirstName() + " " + (u.getLastName() != null ? u.getLastName() : ""))
                .orElse("Unknown");

        String categoryName = categoryRepository.findById(course.getCategoryId())
                .map(Category::getName)
                .orElse("Unknown");

        List<LessonResponse> lessons = course.getLessons() == null ? List.of() :
                course.getLessons().stream()
                        .map(this::toLessonResponse)
                        .toList();

        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .categoryId(course.getCategoryId())
                .categoryName(categoryName)
                .teacherId(course.getTeacherId())
                .teacherName(teacherName.trim())
                .level(course.getLevel())
                .lessonCount(lessons.size())
                .lessons(lessons)
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }

    private LessonResponse toLessonResponse(Lesson lesson) {
        return LessonResponse.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .orderIndex(lesson.getOrderIndex())
                .courseId(lesson.getCourseId())
                .createdAt(lesson.getCreatedAt())
                .updatedAt(lesson.getUpdatedAt())
                .build();
    }
}