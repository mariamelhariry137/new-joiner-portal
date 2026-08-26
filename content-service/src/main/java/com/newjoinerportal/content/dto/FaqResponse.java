package com.newjoinerportal.content.dto;

public record FaqResponse(
        Long id,
        String question,
        String answer,
        String category
) {
}