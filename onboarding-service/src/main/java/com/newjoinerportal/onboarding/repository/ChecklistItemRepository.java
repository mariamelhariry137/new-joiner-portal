package com.newjoinerportal.onboarding.repository;

import com.newjoinerportal.onboarding.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long> {
    List<ChecklistItem> findAllByOrderByOrderIndexAsc();
}