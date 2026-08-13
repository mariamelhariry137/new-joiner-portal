package com.newjoinerportal.content.controller;

import com.newjoinerportal.content.model.LearningResource;
import com.newjoinerportal.content.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/learning-resources")
public class LearningController {

    @Autowired
    private LearningService learningResourceService;

    @GetMapping
    public List<LearningResource> getAllLearningResources() {
        return learningResourceService.getAllLearningResources();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningResource> getLearningResourceById(@PathVariable Long id) {
        LearningResource resource = learningResourceService.getLearningResourceById(id);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<LearningResource> createLearningResource(@RequestBody LearningResource resource) {
        LearningResource created = learningResourceService.createLearningResource(resource);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LearningResource> updateLearningResource(@PathVariable Long id, @RequestBody LearningResource resource) {
        LearningResource updated = learningResourceService.updateLearningResource(id, resource);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLearningResource(@PathVariable Long id) {
        learningResourceService.deleteLearningResource(id);
        return ResponseEntity.noContent().build();
    }
}