package com.newjoinerportal.onboarding.controller;

import com.newjoinerportal.onboarding.entity.ChecklistItem;
import com.newjoinerportal.onboarding.service.ChecklistItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/checklist-items")
public class ChecklistItemController {

    private final ChecklistItemService checklistItemService;

    public ChecklistItemController(ChecklistItemService checklistItemService) {
        this.checklistItemService = checklistItemService;
    }

    @GetMapping
    public List<ChecklistItem> listChecklistItems() {
        return checklistItemService.getAllItemsOrdered();
    }
}