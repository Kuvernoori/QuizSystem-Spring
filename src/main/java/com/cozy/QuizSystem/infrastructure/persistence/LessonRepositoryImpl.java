package com.cozy.QuizSystem.infrastructure.persistence;

import com.cozy.QuizSystem.domain.model.Lesson;
import com.cozy.QuizSystem.domain.repository.LessonRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Builder
public class LessonRepositoryImpl implements LessonRepository {

    private final LessonJpaRepository jpaRepository;
    private final CourseJpaRepository courseJpaRepository;

    @Override
    public List<Lesson> findByCourseId(Long courseId) {
        return jpaRepository.findByCourseIdOrderByOrderIndexAsc(courseId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Lesson save(Lesson lesson) {
        CourseEntity course = courseJpaRepository.findById(lesson.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        LessonEntity entity = LessonEntity.builder()
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .orderIndex(lesson.getOrderIndex())
                .course(course)
                .build();

        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Lesson update(Lesson lesson) {
        LessonEntity entity = jpaRepository.findById(lesson.getId())
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        if (lesson.getTitle() != null) entity.setTitle(lesson.getTitle());
        if (lesson.getContent() != null) entity.setContent(lesson.getContent());
        if (lesson.getOrderIndex() != null) entity.setOrderIndex(lesson.getOrderIndex());

        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        if (!jpaRepository.existsById(id)) {
            throw new RuntimeException("Lesson not found");
        }
        jpaRepository.deleteById(id);
    }

    private Lesson toDomain(LessonEntity entity) {
        return Lesson.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .orderIndex(entity.getOrderIndex())
                .courseId(entity.getCourse().getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}