package com.newjoinerportal.content.controller;

import com.newjoinerportal.content.model.LearningResource;
import com.newjoinerportal.content.service.LearningService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<LearningResource>> getAllLearningResources() {
        return ResponseEntity.ok(learningResourceService.getAllLearningResources());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningResource> getLearningResourceById(@PathVariable Long id) {
        LearningResource resource = learningResourceService.getLearningResourceById(id);
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<LearningResource> createLearningResource(@Valid @RequestBody LearningResource resource) {
        LearningResource created = learningResourceService.createLearningResource(resource);
        return new ResponseEntity<>(created,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LearningResource> updateLearningResource(@PathVariable Long id,@Valid @RequestBody LearningResource resource) {
        LearningResource updated = learningResourceService.updateLearningResource(id, resource);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLearningResource(@PathVariable Long id) {
        learningResourceService.deleteLearningResource(id);
        return ResponseEntity.noContent().build();
    }
}