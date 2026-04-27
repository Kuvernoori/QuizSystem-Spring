package com.cozy.QuizSystem.domain.repository;

import com.cozy.QuizSystem.domain.model.Lesson;
import java.util.List;
import java.util.Optional;

public interface LessonRepository{
    List<Lesson> findByCourseId(Long courseId);
    Optional<Lesson> findById(Long id);
    Lesson save(Lesson lesson);
    Lesson update(Lesson lesson);
    void deleteById(Long id);
}

