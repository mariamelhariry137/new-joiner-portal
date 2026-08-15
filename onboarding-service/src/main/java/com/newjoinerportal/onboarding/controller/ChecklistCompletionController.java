package com.newjoinerportal.onboarding.controller;

import com.newjoinerportal.onboarding.dto.MarkCompletionRequest;
import com.newjoinerportal.onboarding.dto.MarkCompletionResponse;
import com.newjoinerportal.onboarding.service.ChecklistCompletionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/progress")
public class ChecklistCompletionController {

    private final ChecklistCompletionService checklistCompletionService;

    public ChecklistCompletionController(ChecklistCompletionService checklistCompletionService) {
        this.checklistCompletionService = checklistCompletionService;
    }

    @PatchMapping("/{itemId}")
    public MarkCompletionResponse markCompletion(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @RequestBody MarkCompletionRequest request) {
        return checklistCompletionService.markCompletion(userId, itemId, request.completed());
    }
}