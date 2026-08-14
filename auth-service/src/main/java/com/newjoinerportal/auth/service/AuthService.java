package com.newjoinerportal.auth.service;

import com.newjoinerportal.auth.dto.RegisterRequest;
import com.newjoinerportal.auth.dto.RegisterResponse;
import com.newjoinerportal.auth.entity.User;
import com.newjoinerportal.auth.exception.EmailAlreadyExistsException;
import com.newjoinerportal.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.newjoinerportal.auth.dto.LoginRequest;
import com.newjoinerportal.auth.dto.LoginResponse;
import com.newjoinerportal.auth.exception.InvalidCredentialsException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {

        String normalizedEmail =
                request.email().trim().toLowerCase();

        User user = userRepository
                .findByEmailIgnoreCase(normalizedEmail)
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password"
                        )
                );

        if (!passwordEncoder.matches(
                request.password(),
                user.getPasswordHash())) {

            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        return new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse register(RegisterRequest request) {

        String normalizedEmail =
                request.email().trim().toLowerCase();

        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new EmailAlreadyExistsException(
                    "Email is already registered"
            );
        }

        User user = new User();

        user.setEmail(normalizedEmail);

        user.setPasswordHash(
                passwordEncoder.encode(request.password())
        );

        user.setFirstName(request.firstName().trim());
        user.setLastName(request.lastName().trim());

        User savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName()
        );
    }
}