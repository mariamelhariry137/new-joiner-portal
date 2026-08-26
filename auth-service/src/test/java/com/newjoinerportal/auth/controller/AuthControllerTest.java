package com.newjoinerportal.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newjoinerportal.auth.dto.LoginRequest;
import com.newjoinerportal.auth.dto.LoginResponse;
import com.newjoinerportal.auth.dto.RegisterRequest;
import com.newjoinerportal.auth.dto.RegisterResponse;
import com.newjoinerportal.auth.exception.EmailAlreadyExistsException;
import com.newjoinerportal.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Web-layer test: exercises request mapping, bean validation and JSON
 * serialization for AuthController with AuthService mocked out.
 * Security filters are disabled here on purpose -- they are exercised
 * separately by the security config itself, not by this controller slice.
 */
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    void register_returnsCreated_whenRequestIsValid() throws Exception {
        RegisterRequest request = new RegisterRequest(
                "new.joiner@example.com", "password123", "New", "Joiner");
        RegisterResponse response = new RegisterResponse(
                1L, "new.joiner@example.com", "New", "Joiner");

        when(authService.register(any(RegisterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("new.joiner@example.com"));
    }

    @Test
    void register_returnsBadRequest_whenEmailIsInvalid() throws Exception {
        RegisterRequest request = new RegisterRequest(
                "not-an-email", "password123", "New", "Joiner");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").exists());
    }

    @Test
    void register_returnsConflict_whenEmailAlreadyExists() throws Exception {
        RegisterRequest request = new RegisterRequest(
                "existing@example.com", "password123", "New", "Joiner");

        when(authService.register(any(RegisterRequest.class)))
                .thenThrow(new EmailAlreadyExistsException("Email is already registered"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Email is already registered"));
    }

    @Test
    void login_returnsOk_whenCredentialsAreValid() throws Exception {
        LoginRequest request = new LoginRequest("new.joiner@example.com", "password123");
        LoginResponse response = new LoginResponse(
                1L, "new.joiner@example.com", "New", "Joiner", "token", "Bearer", 3600L);

        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("token"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"));

        verify(authService).login(any(LoginRequest.class));
    }

    @Test
    void login_returnsBadRequest_whenPasswordIsBlank() throws Exception {
        LoginRequest request = new LoginRequest("new.joiner@example.com", "");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
