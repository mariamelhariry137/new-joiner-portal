package com.newjoinerportal.auth.controller;

import com.newjoinerportal.auth.dto.ProfileResponse;
import com.newjoinerportal.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/auth")
public class CurrentUserController {

    private final AuthService authService;

    public CurrentUserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getCurrentUser(
            Authentication authentication) {

        ProfileResponse profile =
                authService.getCurrentUserProfile(
                        authentication.getName()
                );

        return ResponseEntity.ok(profile);
    }
}