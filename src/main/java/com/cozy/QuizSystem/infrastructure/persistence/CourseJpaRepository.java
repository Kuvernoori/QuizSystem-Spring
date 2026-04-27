package com.cozy.QuizSystem.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseJpaRepository extends JpaRepository<CourseEntity, Long> {
    List<CourseEntity> findByCategoryId(Long categoryId);
    List<CourseEntity> findByTeacherId(Long teacherId);

}
