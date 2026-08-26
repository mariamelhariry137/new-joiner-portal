package com.newjoinerportal.content.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newjoinerportal.content.model.Policy;
import com.newjoinerportal.content.service.PolicyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Web-layer test for PolicyController with PolicyService mocked out.
 * Security filters are disabled here on purpose -- authentication/
 * authorization is exercised elsewhere, this slice only checks request
 * mapping, validation and JSON shape.
 */
@WebMvcTest(PolicyController.class)
@AutoConfigureMockMvc(addFilters = false)
class PolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PolicyService policyService;

    @Test
    void getAllPolicies_returnsOkWithList() throws Exception {
        Policy policy = new Policy("Leave Policy", "Details about leave");
        policy.setId(1L);

        when(policyService.getAllPolicies()).thenReturn(List.of(policy));

        mockMvc.perform(get("/policies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Leave Policy"));
    }

    @Test
    void getPolicyById_returnsOk_whenPolicyExists() throws Exception {
        Policy policy = new Policy("Leave Policy", "Details about leave");
        policy.setId(1L);

        when(policyService.getPolicyById(1L)).thenReturn(policy);

        mockMvc.perform(get("/policies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Leave Policy"));
    }

    @Test
    void createPolicy_returnsCreated_whenValidRequest() throws Exception {
        Policy request = new Policy("Remote Work", "Details about remote work");
        Policy saved = new Policy("Remote Work", "Details about remote work");
        saved.setId(2L);

        when(policyService.createPolicy(any(Policy.class))).thenReturn(saved);

        mockMvc.perform(post("/policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));
    }
}
