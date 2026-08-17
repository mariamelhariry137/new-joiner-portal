package com.newjoinerportal.onboarding.dto;

import java.time.LocalDateTime;

public class ChecklistProgressResponse {

    private Long checklistItemId;
    private String title;
    private Integer orderIndex;
    private boolean completed;
    private LocalDateTime completedAt;

    public ChecklistProgressResponse(Long checklistItemId, String title, Integer orderIndex,
                                     boolean completed, LocalDateTime completedAt) {
        this.checklistItemId = checklistItemId;
        this.title = title;
        this.orderIndex = orderIndex;
        this.completed = completed;
        this.completedAt = completedAt;
    }

    public Long getChecklistItemId() { return checklistItemId; }
    public String getTitle() { return title; }
    public Integer getOrderIndex() { return orderIndex; }
    public boolean isCompleted() { return completed; }
    public LocalDateTime getCompletedAt() { return completedAt; }
}