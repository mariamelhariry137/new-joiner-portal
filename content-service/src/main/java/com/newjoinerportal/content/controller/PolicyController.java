package com.newjoinerportal.content.controller;

import com.newjoinerportal.content.model.Policy;
import com.newjoinerportal.content.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/policies")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @GetMapping
    public List<Policy> getAllPolicies() {
        return policyService.getAllPolicies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Policy> getPolicyById(@PathVariable Long id) {
        Policy policy = policyService.getPolicyById(id);
        if (policy == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(policy);
    }

    @PostMapping
    public ResponseEntity<Policy> createPolicy(@RequestBody Policy policy) {
        Policy created = policyService.createPolicy(policy);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Policy> updatePolicy(@PathVariable Long id, @RequestBody Policy policy) {
        Policy updated = policyService.updatePolicy(id, policy);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        policyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }
}