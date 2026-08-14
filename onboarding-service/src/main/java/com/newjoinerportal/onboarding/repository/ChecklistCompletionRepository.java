package com.newjoinerportal.onboarding.repository;

import com.newjoinerportal.onboarding.ChecklistCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ChecklistCompletionRepository extends JpaRepository<ChecklistCompletion, Long> {
    List<ChecklistCompletion> findAllByUserId(Long userId);
    Optional<ChecklistCompletion> findByUserIdAndChecklistItemId(Long userId, Long checklistItemId);
}