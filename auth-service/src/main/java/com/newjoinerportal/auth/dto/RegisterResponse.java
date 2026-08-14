package com.newjoinerportal.auth.dto;

public record RegisterResponse(
        Long id,
        String email,
        String firstName,
        String lastName
) {
}