package com.newjoinerportal.content.repo;

import com.newjoinerportal.content.model.UserQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserQuestionRepository
        extends JpaRepository<UserQuestion, Long> {

    // Used by the logged-in user.
    // Returns only that user's questions.
    List<UserQuestion> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Used later by the admin to see submitted questions.
    List<UserQuestion> findAllByOrderByCreatedAtDesc();
}