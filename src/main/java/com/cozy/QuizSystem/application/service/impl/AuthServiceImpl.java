package com.cozy.QuizSystem.application.service.impl;

import com.cozy.QuizSystem.application.dto.AuthResponse;
import com.cozy.QuizSystem.application.dto.LoginRequest;
import com.cozy.QuizSystem.application.dto.RegisterRequest;
import com.cozy.QuizSystem.application.dto.UserResponse;
import com.cozy.QuizSystem.application.service.AuthService;
import com.cozy.QuizSystem.domain.model.User;
import com.cozy.QuizSystem.domain.repository.UserRepository;
import com.cozy.QuizSystem.infrastructure.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone already registered");
        }

        User user = User.builder()
                .phone(request.getPhone())
                .role(request.getRole())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .secondName(request.getSecondName())
                .birthDate(request.getBirthDate())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        User saved = userRepository.save(user);
        String token = jwtService.generateToken(saved.getPhone(), saved.getRole());
        return new AuthResponse(token, saved.getRole());
    }
        @Override
        public AuthResponse login (LoginRequest request){
            User user = userRepository.findByPhone(request.getPhone())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Wrong password");
            }
            String token = jwtService.generateToken(user.getPhone(), user.getRole());
            return new AuthResponse(token, user.getRole());
        }

        @Override
        public UserResponse getMe (String phone) {
            User user = userRepository.findByPhone(phone)
                    .orElseThrow(() -> new RuntimeException("User not found"));
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

