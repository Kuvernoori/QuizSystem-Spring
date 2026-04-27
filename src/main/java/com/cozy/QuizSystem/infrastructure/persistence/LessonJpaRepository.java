package com.cozy.QuizSystem.infrastructure.persistence;


import com.cozy.QuizSystem.domain.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonJpaRepository extends JpaRepository <LessonEntity, Long> {
    List<LessonEntity> findByCourseIdOrderByOrderIndexAsc(Long courseIdn);
}
