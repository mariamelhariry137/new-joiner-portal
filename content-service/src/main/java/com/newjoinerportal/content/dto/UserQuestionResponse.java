package com.newjoinerportal.content.dto;

import com.newjoinerportal.content.model.QuestionStatus;

import java.time.LocalDateTime;

public record UserQuestionResponse(
        Long id,
        String question,
        QuestionStatus status,
        String adminResponse,
        LocalDateTime createdAt,
        LocalDateTime answeredAt
) {
}