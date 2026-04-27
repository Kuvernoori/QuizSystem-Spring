package com.cozy.QuizSystem.domain.repository;

import com.cozy.QuizSystem.domain.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category save(Category category);
    void deleteById(Long id);
    boolean existsByName(String name);
}
