package com.newjoinerportal.onboarding.controller;

import com.newjoinerportal.onboarding.dto.MarkCompletionRequest;
import com.newjoinerportal.onboarding.dto.MarkCompletionResponse;
import com.newjoinerportal.onboarding.dto.ChecklistProgressResponse;
import com.newjoinerportal.onboarding.security.CurrentUserResolver;
import com.newjoinerportal.onboarding.service.ChecklistCompletionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
public class ChecklistCompletionController {

    private final ChecklistCompletionService checklistCompletionService;
    private final CurrentUserResolver currentUserResolver;

    public ChecklistCompletionController(ChecklistCompletionService checklistCompletionService,
                                         CurrentUserResolver currentUserResolver) {
        this.checklistCompletionService = checklistCompletionService;
        this.currentUserResolver = currentUserResolver;
    }

    @GetMapping
    @Operation(summary = "Get the authenticated user's onboarding progress")
    public List<ChecklistProgressResponse> getUserProgress(HttpServletRequest request) {
        Long userId = currentUserResolver.resolve(request);
        return checklistCompletionService.getUserProgress(userId);
    }

    @PatchMapping("/{itemId}")
    @Operation(summary = "Mark a checklist item complete or incomplete for the authenticated user")
    public MarkCompletionResponse markCompletion(
            HttpServletRequest request,
            @PathVariable Long itemId,
            @RequestBody MarkCompletionRequest markRequest) {
        Long userId = currentUserResolver.resolve(request);
        return checklistCompletionService.markCompletion(userId, itemId, markRequest.completed());
    }
}