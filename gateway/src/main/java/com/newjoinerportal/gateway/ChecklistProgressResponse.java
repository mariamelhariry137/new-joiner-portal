package com.newjoinerportal.gateway;

import java.time.LocalDateTime;

public class ChecklistProgressResponse {

    private Long checklistItemId;
    private String title;
    private Integer orderIndex;
    private boolean completed;
    private LocalDateTime completedAt;
    private double completionPercentage;

    public ChecklistProgressResponse() {}

    public ChecklistProgressResponse(Long checklistItemId, String title, Integer orderIndex,
                                     boolean completed, LocalDateTime completedAt, double completionPercentage) {
        this.checklistItemId = checklistItemId;
        this.title = title;
        this.orderIndex = orderIndex;
        this.completed = completed;
        this.completedAt = completedAt;
        this.completionPercentage = completionPercentage;
    }

    public Long getChecklistItemId() { return checklistItemId; }
    public String getTitle() { return title; }
    public Integer getOrderIndex() { return orderIndex; }
    public boolean isCompleted() { return completed; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public double getCompletionPercentage() { return completionPercentage; }

    public void setChecklistItemId(Long checklistItemId) { this.checklistItemId = checklistItemId; }
    public void setTitle(String title) { this.title = title; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
    public void setCompletionPercentage(double completionPercentage) { this.completionPercentage = completionPercentage; }
}