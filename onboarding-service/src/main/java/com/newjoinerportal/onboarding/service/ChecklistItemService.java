package com.newjoinerportal.onboarding.service;

import com.newjoinerportal.onboarding.entity.ChecklistItem;
import com.newjoinerportal.onboarding.repository.ChecklistItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChecklistItemService {

    private final ChecklistItemRepository checklistItemRepository;

    public ChecklistItemService(ChecklistItemRepository checklistItemRepository) {
        this.checklistItemRepository = checklistItemRepository;
    }

    public List<ChecklistItem> getAllItemsOrdered() {
        return checklistItemRepository.findAllByOrderByOrderIndexAsc();
    }
}