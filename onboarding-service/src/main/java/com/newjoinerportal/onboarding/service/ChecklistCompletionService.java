package com.newjoinerportal.onboarding.service;

import com.newjoinerportal.onboarding.ChecklistCompletion;
import com.newjoinerportal.onboarding.dto.ChecklistProgressResponse;
import com.newjoinerportal.onboarding.ChecklistItem;
import com.newjoinerportal.onboarding.repository.ChecklistCompletionRepository;
import com.newjoinerportal.onboarding.repository.ChecklistItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChecklistCompletionService {

    private final ChecklistItemRepository checklistItemRepository;
    private final ChecklistCompletionRepository checklistCompletionRepository;

    public ChecklistCompletionService(ChecklistItemRepository checklistItemRepository,
                                      ChecklistCompletionRepository checklistCompletionRepository) {
        this.checklistItemRepository = checklistItemRepository;
        this.checklistCompletionRepository = checklistCompletionRepository;
    }

    public List<ChecklistProgressResponse> getUserProgress(Long userId) {
        List<ChecklistItem> allItems = checklistItemRepository.findAllByOrderByOrderIndexAsc();

        Map<Long, ChecklistCompletion> completionsByItemId = checklistCompletionRepository
                .findAllByUserId(userId)
                .stream()
                .collect(Collectors.toMap(ChecklistCompletion::getChecklistItemId, c -> c));

        return allItems.stream()
                .map(item -> {
                    ChecklistCompletion completion = completionsByItemId.get(item.getId());
                    boolean isCompleted = completion != null && completion.isCompleted();
                    return new ChecklistProgressResponse(
                            item.getId(),
                            item.getTitle(),
                            item.getOrderIndex(),
                            isCompleted,
                            completion != null ? completion.getCompletedAt() : null
                    );
                })
                .toList();
    }
}