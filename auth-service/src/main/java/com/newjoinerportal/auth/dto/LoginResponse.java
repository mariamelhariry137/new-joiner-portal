package com.newjoinerportal.auth.dto;

public record LoginResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String accessToken,
        String tokenType,
        long expiresIn
) {
}