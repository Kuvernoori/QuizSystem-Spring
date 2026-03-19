package com.cozy.QuizSystem.domain.repository;

import com.cozy.QuizSystem.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByPhone (String phone);
    Optional<User> findByEmail(String email);
    boolean existsByPhone (String phone);

    Optional<User> findById(Long id);
    User update(User user);
    List<User> findAll();
    void deleteById(Long id);
}
