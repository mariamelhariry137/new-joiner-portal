package com.newjoinerportal.onboarding.controller;

import com.newjoinerportal.onboarding.ChecklistItem;
import com.newjoinerportal.onboarding.security.CurrentUserResolver;
import com.newjoinerportal.onboarding.service.ChecklistItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/checklist-items")
public class ChecklistItemController {

    private final ChecklistItemService checklistItemService;
    private final CurrentUserResolver currentUserResolver;

    public ChecklistItemController(ChecklistItemService checklistItemService,
                                   CurrentUserResolver currentUserResolver) {
        this.checklistItemService = checklistItemService;
        this.currentUserResolver = currentUserResolver;
    }

    @GetMapping
    @Operation(summary = "List all onboarding checklist items", description = "Returns every checklist item in display order. Requires authentication.")
    public List<ChecklistItem> listChecklistItems(HttpServletRequest request) {
        currentUserResolver.resolve(request); // throws AuthenticatedUserException if missing/invalid/expired
        return checklistItemService.getAllItemsOrdered();
    }
}