package com.newjoinerportal.onboarding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newjoinerportal.onboarding.dto.MarkCompletionRequest;
import com.newjoinerportal.onboarding.security.CurrentUserResolver;
import com.newjoinerportal.onboarding.service.ChecklistCompletionService;
import com.newjoinerportal.onboarding.service.ChecklistItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChecklistCompletionController.class)
class ChecklistCompletionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ChecklistCompletionService checklistCompletionService;

    @MockBean
    private CurrentUserResolver currentUserResolver;

    @Test
    void markCompletion_unknownItem_returns404WithCleanErrorBody() throws Exception {
        Long userId = 1L;
        Long unknownItemId = 999L;
        MarkCompletionRequest request = new MarkCompletionRequest(true);

        when(currentUserResolver.resolve(any())).thenReturn(userId);
        when(checklistCompletionService.markCompletion(eq(userId), eq(unknownItemId), eq(true)))
                .thenThrow(new ChecklistItemNotFoundException("No checklist item found with id " + unknownItemId));

        mockMvc.perform(patch("/progress/{itemId}", unknownItemId)
                        .header("Authorization", "Bearer fake-token-for-test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("No checklist item found with id 999"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void markCompletion_knownItem_returns200() throws Exception {
        Long userId = 1L;
        Long itemId = 1L;
        MarkCompletionRequest request = new MarkCompletionRequest(true);

        when(currentUserResolver.resolve(any())).thenReturn(userId);
        when(checklistCompletionService.markCompletion(eq(userId), eq(itemId), eq(true)))
                .thenReturn(new com.newjoinerportal.onboarding.dto.MarkCompletionResponse(
                        itemId, true, java.time.LocalDateTime.now(), 25.0));

        mockMvc.perform(patch("/progress/{itemId}", itemId)
                        .header("Authorization", "Bearer fake-token-for-test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true))
                .andExpect(jsonPath("$.completionPercentage").value(25.0));
    }
}