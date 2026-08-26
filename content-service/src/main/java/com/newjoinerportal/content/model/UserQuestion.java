package com.newjoinerportal.content.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_questions")
public class UserQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private QuestionStatus status = QuestionStatus.PENDING;

    @Column(name = "admin_response", columnDefinition = "TEXT")
    private String adminResponse;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "answered_at")
    private LocalDateTime answeredAt;


    protected UserQuestion() {
    }


    public UserQuestion(
            Long userId,
            String question
    ) {
        this.userId = userId;
        this.question = question;
        this.status = QuestionStatus.PENDING;
    }


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();

        if (status == null) {
            status = QuestionStatus.PENDING;
        }
    }


    public void answer(String response) {
        this.adminResponse = response;
        this.status = QuestionStatus.ANSWERED;
        this.answeredAt = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getQuestion() {
        return question;
    }

    public QuestionStatus getStatus() {
        return status;
    }

    public String getAdminResponse() {
        return adminResponse;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getAnsweredAt() {
        return answeredAt;
    }
}