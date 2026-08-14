package com.newjoinerportal.onboarding.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "checklist_items")
public class ChecklistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer orderIndex;

    public ChecklistItem() {}

    public ChecklistItem(String title, Integer orderIndex) {
        this.title = title;
        this.orderIndex = orderIndex;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }


}

