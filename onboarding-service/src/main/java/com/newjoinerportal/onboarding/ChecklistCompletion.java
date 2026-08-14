package com.newjoinerportal.onboarding;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "checklist_completions")
public class ChecklistCompletion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "checklist_item_id", nullable = false)
    private Long checklistItemId;

    @Column(nullable = false)
    private boolean completed;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public ChecklistCompletion() {}

    public ChecklistCompletion(Long userId, Long checklistItemId, boolean completed) {
        this.userId = userId;
        this.checklistItemId = checklistItemId;
        this.completed = completed;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getChecklistItemId() { return checklistItemId; }
    public void setChecklistItemId(Long checklistItemId) { this.checklistItemId = checklistItemId; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}
