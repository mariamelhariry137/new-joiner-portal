package com.newjoinerportal.auth.dto;

public record ProfileResponse(
        Long id,
        String email,
        String firstName,
        String lastName
) {
}