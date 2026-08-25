package com.newjoinerportal.onboarding;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

@Entity
@Table(name = "checklist_items")
public class ChecklistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String description = "";

    @Column(nullable = false)
    private Integer orderIndex;

    public ChecklistItem() {
        this.description = "";
    }

    public ChecklistItem(String title, Integer orderIndex) {
        this.title = title;
        this.description = "";
        this.orderIndex = orderIndex;
    }

    public ChecklistItem(String title, String description, Integer orderIndex) {
        this.title = title;
        this.description = description == null ? "" : description;
        this.orderIndex = orderIndex;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public String getDescription() { return description == null ? "" : description; }
    public void setDescription(String description) { this.description = description == null ? "" : description; }
    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }
}