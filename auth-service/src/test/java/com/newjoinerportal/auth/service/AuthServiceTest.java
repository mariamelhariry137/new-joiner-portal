package com.newjoinerportal.auth.service;

import com.newjoinerportal.auth.dto.LoginRequest;
import com.newjoinerportal.auth.dto.RegisterRequest;
import com.newjoinerportal.auth.dto.RegisterResponse;
import com.newjoinerportal.auth.entity.User;
import com.newjoinerportal.auth.entity.UserRole;
import com.newjoinerportal.auth.exception.EmailAlreadyExistsException;
import com.newjoinerportal.auth.exception.InvalidCredentialsException;
import com.newjoinerportal.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pure unit tests for AuthService business logic -- no Spring context,
 * collaborators are mocked with Mockito.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(userRepository, passwordEncoder, jwtService);
    }

    @Test
    void register_savesUser_whenEmailIsNotTaken() {
        RegisterRequest request = new RegisterRequest(
                "New.Joiner@Example.com", "password123", "New", "Joiner");

        when(userRepository.existsByEmailIgnoreCase("new.joiner@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setEmail("new.joiner@example.com");
            return user;
        });

        RegisterResponse response = authService.register(request);

        assertThat(response.email()).isEqualTo("new.joiner@example.com");
        assertThat(response.firstName()).isEqualTo("New");

        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_throwsEmailAlreadyExists_whenEmailIsTaken() {
        RegisterRequest request = new RegisterRequest(
                "existing@example.com", "password123", "New", "Joiner");

        when(userRepository.existsByEmailIgnoreCase("existing@example.com")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(EmailAlreadyExistsException.class);
    }

    @Test
    void login_throwsInvalidCredentials_whenUserDoesNotExist() {
        LoginRequest request = new LoginRequest("missing@example.com", "password123");

        when(userRepository.findByEmailIgnoreCase("missing@example.com"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void login_throwsInvalidCredentials_whenPasswordDoesNotMatch() {
        LoginRequest request = new LoginRequest("new.joiner@example.com", "wrong-password");
        User user = new User();
        user.setEmail("new.joiner@example.com");
        user.setPasswordHash("hashed");
        user.setRole(UserRole.NEW_JOINER);

        when(userRepository.findByEmailIgnoreCase("new.joiner@example.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void login_returnsAccessToken_whenCredentialsAreValid() {
        LoginRequest request = new LoginRequest("new.joiner@example.com", "password123");
        User user = new User();
        user.setEmail("new.joiner@example.com");
        user.setPasswordHash("hashed");
        user.setFirstName("New");
        user.setLastName("Joiner");
        user.setRole(UserRole.NEW_JOINER);

        when(userRepository.findByEmailIgnoreCase("new.joiner@example.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "hashed")).thenReturn(true);
        when(jwtService.generateAccessToken(user)).thenReturn("signed-token");
        when(jwtService.getExpirationSeconds()).thenReturn(3600L);

        var response = authService.login(request);

        assertThat(response.accessToken()).isEqualTo("signed-token");
        assertThat(response.expiresIn()).isEqualTo(3600L);
    }
}
