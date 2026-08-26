package com.newjoinerportal.content.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateFaqRequest(

        @NotBlank(message = "Question is required")
        @Size(max = 500)
        String question,

        @NotBlank(message = "Answer is required")
        String answer,

        @Size(max = 100)
        String category

) {
}