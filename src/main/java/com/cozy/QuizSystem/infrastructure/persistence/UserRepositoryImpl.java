package com.cozy.QuizSystem.infrastructure.persistence;
import com.cozy.QuizSystem.domain.model.User;
import com.cozy.QuizSystem.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository jpaRepository;

    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);
        UserEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<User> findByPhone (String phone) {
        return jpaRepository.findByPhone(phone)
                .map(this::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(this::toDomain);
    }

    @Override
    public boolean existsByPhone (String phone) {
        return jpaRepository.existsByPhone(phone);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }
    @Override
    public List<User> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        if (!jpaRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        jpaRepository.deleteById(id);
    }

    @Override
    public User update(User user) {
        UserEntity entity = jpaRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getFirstName() != null) entity.setFirstName(user.getFirstName());
        if (user.getLastName() != null) entity.setLastName(user.getLastName());
        if (user.getSecondName() != null) entity.setSecondName(user.getSecondName());
        if (user.getEmail() != null) entity.setEmail(user.getEmail());
        if (user.getPassword() != null) entity.setPassword(user.getPassword());
        if (user.getRole() != null) entity.setRole(user.getRole());
        if (user.getBirthDate() != null) entity.setBirthDate(user.getBirthDate());

        UserEntity saved = jpaRepository.save(entity);
        return toDomain(saved);

    }

    private UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setPhone(user.getPhone());
        entity.setRole(user.getRole());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setSecondName(user.getSecondName());
        entity.setBirthDate(user.getBirthDate());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        return entity;
    }

    private User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getPhone(),
                entity.getRole(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getSecondName(),
                entity.getBirthDate(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
