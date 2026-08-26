package com.newjoinerportal.onboarding.controller;

import com.newjoinerportal.onboarding.ChecklistItem;
import com.newjoinerportal.onboarding.security.AuthenticatedUserException;
import com.newjoinerportal.onboarding.security.CurrentUserResolver;
import com.newjoinerportal.onboarding.service.ChecklistItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Web-layer test for ChecklistItemController. Authentication here is a
 * plain @Component (CurrentUserResolver) called directly by the controller
 * rather than a servlet filter, so it is mocked like any other collaborator
 * instead of needing addFilters(false).
 */
@WebMvcTest(ChecklistItemController.class)
class ChecklistItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChecklistItemService checklistItemService;

    @MockBean
    private CurrentUserResolver currentUserResolver;

    @Test
    void listChecklistItems_returnsOrderedItems_whenAuthenticated() throws Exception {
        when(currentUserResolver.resolve(any(HttpServletRequest.class))).thenReturn(1L);
        when(checklistItemService.getAllItemsOrdered()).thenReturn(List.of(
                new ChecklistItem("Set up laptop", "Install required tooling", 1),
                new ChecklistItem("Meet your buddy", "Intro call with onboarding buddy", 2)
        ));

        mockMvc.perform(get("/checklist-items")
                        .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Set up laptop"))
                .andExpect(jsonPath("$[1].orderIndex").value(2));
    }

    @Test
    void listChecklistItems_returnsUnauthorized_whenTokenMissing() throws Exception {
        when(currentUserResolver.resolve(any(HttpServletRequest.class)))
                .thenThrow(new AuthenticatedUserException("Missing or invalid Authorization header"));

        mockMvc.perform(get("/checklist-items"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401));
    }
}
