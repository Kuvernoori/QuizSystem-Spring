package com.cozy.QuizSystem.infrastructure.persistence;

import com.cozy.QuizSystem.domain.model.Course;
import com.cozy.QuizSystem.domain.model.Lesson;
import com.cozy.QuizSystem.domain.repository.CourseRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Builder
public class CourseRepositoryImpl implements CourseRepository {

    private final CourseJpaRepository jpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public List<Course> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Course> findByCategoryId(Long categoryId) {
        return jpaRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Course> findByTeacherId(Long teacherId) {
        return jpaRepository.findByTeacherId(teacherId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Course> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Course save(Course course) {
        CategoryEntity category = categoryJpaRepository.findById(course.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        CourseEntity entity = CourseEntity.builder()
                .title(course.getTitle())
                .description(course.getDescription())
                .category(category)
                .teacherId(course.getTeacherId())
                .level(course.getLevel())
                .build();

        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Course update(Course course) {
        CourseEntity entity = jpaRepository.findById(course.getId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (course.getTitle() != null) entity.setTitle(course.getTitle());
        if (course.getDescription() != null) entity.setDescription(course.getDescription());
        if (course.getLevel() != null) entity.setLevel(course.getLevel());

        if (course.getCategoryId() != null) {
            CategoryEntity category = categoryJpaRepository.findById(course.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            entity.setCategory(category);
        }

        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        if (!jpaRepository.existsById(id)) {
            throw new RuntimeException("Course not found");
        }
        jpaRepository.deleteById(id);
    }

    private Course toDomain(CourseEntity entity) {
        List<Lesson> lessons = entity.getLessons() == null ? List.of() :
                entity.getLessons().stream()
                        .map(l -> Lesson.builder()
                                .id(l.getId())
                                .title(l.getTitle())
                                .content(l.getContent())
                                .orderIndex(l.getOrderIndex())
                                .courseId(entity.getId())
                                .createdAt(l.getCreatedAt())
                                .updatedAt(l.getUpdatedAt())
                                .build())
                        .toList();

        return Course.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .categoryId(entity.getCategory().getId())
                .teacherId(entity.getTeacherId())
                .level(entity.getLevel())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .lessons(lessons)
                .build();
    }
}