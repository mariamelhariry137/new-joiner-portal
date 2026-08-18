package com.newjoinerportal.onboarding.controller;

import com.newjoinerportal.onboarding.dto.ChecklistProgressResponse;
import com.newjoinerportal.onboarding.dto.MarkCompletionRequest;
import com.newjoinerportal.onboarding.dto.MarkCompletionResponse;
import com.newjoinerportal.onboarding.service.ChecklistCompletionService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/progress")
public class ChecklistCompletionController {

    private final ChecklistCompletionService checklistCompletionService;

    public ChecklistCompletionController(ChecklistCompletionService checklistCompletionService) {
        this.checklistCompletionService = checklistCompletionService;
    }


    @GetMapping
    @Operation(summary = "Get a user's onboarding progress", description = "Returns every checklist item along with whether this user has completed it.")
    public List<ChecklistProgressResponse> getUserProgress(@PathVariable Long userId) {
        return checklistCompletionService.getUserProgress(userId);
    }

    @PatchMapping("/{itemId}")
    @Operation(summary = "Mark a checklist item complete or incomplete", description = "Updates completion status for one item and returns the user's updated completion percentage.")
    public MarkCompletionResponse markCompletion(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @RequestBody MarkCompletionRequest request) {
        return checklistCompletionService.markCompletion(userId, itemId, request.completed());
    }
}