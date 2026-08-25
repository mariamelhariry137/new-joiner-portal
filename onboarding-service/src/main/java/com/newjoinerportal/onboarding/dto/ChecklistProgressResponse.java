package com.newjoinerportal.onboarding.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

public class ChecklistProgressResponse {

    private Long checklistItemId;
    private String title;
    private String description = "";
    private Integer orderIndex;
    private boolean completed;
    private LocalDateTime completedAt;
    private double completionPercentage;

    public ChecklistProgressResponse(Long checklistItemId, String title, String description,
                                     Integer orderIndex, boolean completed,
                                     LocalDateTime completedAt, double completionPercentage) {
        this.checklistItemId = checklistItemId;
        this.title = title;
        this.description = description == null ? "" : description;
        this.orderIndex = orderIndex;
        this.completed = completed;
        this.completedAt = completedAt;
        this.completionPercentage = completionPercentage;
    }

    public Long getChecklistItemId() { return checklistItemId; }
    public String getTitle() { return title; }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public String getDescription() { return description == null ? "" : description; }
    public Integer getOrderIndex() { return orderIndex; }
    public boolean isCompleted() { return completed; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public double getCompletionPercentage() { return completionPercentage; }
}