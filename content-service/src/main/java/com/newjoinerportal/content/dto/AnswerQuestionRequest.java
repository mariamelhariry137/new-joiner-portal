package com.newjoinerportal.content.dto;

import jakarta.validation.constraints.NotBlank;

public record AnswerQuestionRequest(

        @NotBlank(message = "Answer is required")
        String answer

) {
}