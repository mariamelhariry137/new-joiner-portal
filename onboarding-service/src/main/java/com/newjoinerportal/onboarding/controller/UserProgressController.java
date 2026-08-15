package com.newjoinerportal.onboarding.controller;

import com.newjoinerportal.onboarding.dto.ChecklistProgressResponse;
import com.newjoinerportal.onboarding.service.ChecklistCompletionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/progress")
public class UserProgressController {

    private final ChecklistCompletionService checklistCompletionService;

    public UserProgressController(ChecklistCompletionService checklistCompletionService) {
        this.checklistCompletionService = checklistCompletionService;
    }

    @GetMapping
    public List<ChecklistProgressResponse> getUserProgress(@PathVariable Long userId) {
        return checklistCompletionService.getUserProgress(userId);
    }
}