package com.newjoinerportal.content.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "learning_resources")
public class LearningResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(min=2,max=20)
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "description of resouce cant be blank")
    @Column(columnDefinition = "TEXT",nullable = false)
    private String description;

    @NotBlank(message = "url of resouce cant be blank")
    @Column(columnDefinition = "TEXT",nullable = false)
    private String url;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public LearningResource() {}

    public LearningResource(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}