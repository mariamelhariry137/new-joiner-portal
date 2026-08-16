package com.newjoinerportal.onboarding.controller;

import com.newjoinerportal.onboarding.ChecklistItem;
import com.newjoinerportal.onboarding.service.ChecklistItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/checklist-items")
public class ChecklistItemController {

    private final ChecklistItemService checklistItemService;

    public ChecklistItemController(ChecklistItemService checklistItemService) {
        this.checklistItemService = checklistItemService;
    }
    

    @GetMapping
    @Operation(summary = "List all onboarding checklist items", description = "Returns every checklist item in display order.")
    public List<ChecklistItem> listChecklistItems() {
        return checklistItemService.getAllItemsOrdered();
    }
}