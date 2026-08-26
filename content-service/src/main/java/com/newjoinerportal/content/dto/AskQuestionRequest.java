package com.newjoinerportal.content.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AskQuestionRequest(

        @NotBlank(message = "Question is required")
        @Size(
                max = 2000,
                message = "Question must not exceed 2000 characters"
        )
        String question

) {
}