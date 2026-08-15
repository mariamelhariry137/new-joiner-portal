package com.newjoinerportal.onboarding.dto;

import java.time.LocalDateTime;

public record MarkCompletionResponse(
        Long checklistItemId,
        boolean completed,
        LocalDateTime completedAt,
        double completionPercentage
) {}