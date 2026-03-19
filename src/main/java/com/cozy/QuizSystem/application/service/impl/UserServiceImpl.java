package com.cozy.QuizSystem.application.service.impl;
import com.cozy.QuizSystem.application.dto.*;
import com.cozy.QuizSystem.application.service.UserService;
import com.cozy.QuizSystem.domain.model.User;
import com.cozy.QuizSystem.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getProfile(String phone){
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toResponse(user);
    }

    @Override
    public UserResponse updateProfile(String phone, UpdateProfileRequest request){
        User existing = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User updated = new User (
                existing.getId(),
                existing.getPhone(),
                existing.getRole(),
                request.getFirstName() != null
                        ? request.getFirstName() : existing.getFirstName(),
                request.getLastName() != null
                        ? request.getLastName() : existing.getLastName(),
                request.getSecondName() != null
                        ? request.getSecondName() : existing.getSecondName(),
                existing.getBirthDate(),
                request.getEmail() != null
                        ? request.getEmail() : existing.getEmail(),
                existing.getPassword(),
                existing.getCreatedAt(),
                existing.getUpdatedAt()
        );
        User saved = userRepository.update(updated);
        return toResponse(saved);
    }

    @Override
    public void updatePassword(String phone, UpdatePasswordRequest request){
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        User updated = new User(
                user.getId(),
                user.getPhone(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getSecondName(),
                user.getBirthDate(),
                user.getEmail(),
                passwordEncoder.encode(request.getNewPassword()),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
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
