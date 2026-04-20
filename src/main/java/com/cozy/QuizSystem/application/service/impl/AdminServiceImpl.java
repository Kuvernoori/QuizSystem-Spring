package com.cozy.QuizSystem.application.service.impl;

import com.cozy.QuizSystem.application.dto.*;
import com.cozy.QuizSystem.application.service.AdminService;
import com.cozy.QuizSystem.domain.model.User;
import com.cozy.QuizSystem.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toResponse(user);
    }

    @Override
    public UserResponse updateProfile(Long id, AdminUpdateProfileRequest request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User updated = User.builder()
                .id(existing.getId())
                .phone(existing.getPhone())
                .role(existing.getRole())
                .birthDate(existing.getBirthDate())
                .firstName(request.getFirstName() != null
                        ? request.getFirstName() : existing.getFirstName())
                .lastName(request.getLastName() != null
                        ? request.getLastName() : existing.getLastName())
                .secondName(request.getSecondName() != null
                        ? request.getSecondName() : existing.getSecondName())
                .email(request.getEmail() != null
                        ? request.getEmail() : existing.getEmail())
                .build();
        return toResponse(userRepository.update(updated));
    }

    @Override
    public void updatePassword(Long id, AdminUpdatePasswordRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User updated = User.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .role(user.getRole())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .secondName(user.getSecondName())
                .birthDate(user.getBirthDate())
                .email(user.getEmail())
                .password(passwordEncoder.encode(request.getNewPassword()))
                .createdAt(user.getCreatedAt())
                .build();
        userRepository.update(updated);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void changeRole(Long id, String role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String newRole = role.toUpperCase();
        if (!newRole.equals("STUDENT") && !newRole.equals("ADMIN") && !newRole.equals("TEACHER")) {
            throw new RuntimeException("Invalid role, must be student or admin");
        }

    User updated = User.builder()
            .id(user.getId())
            .phone(user.getPhone())
            .role(newRole)
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .secondName(user.getSecondName())
            .email(user.getEmail())
            .password(user.getPassword())
            .createdAt(user.getCreatedAt())
            .build();
    userRepository.update(updated);
}
    private UserResponse toResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getPhone(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getSecondName(),
                user.getBirthDate(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
