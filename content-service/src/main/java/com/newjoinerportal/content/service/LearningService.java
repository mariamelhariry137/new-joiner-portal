package com.newjoinerportal.content.service;

import com.newjoinerportal.content.model.LearningResource;
import com.newjoinerportal.content.repo.LearningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LearningService {

    @Autowired
    private LearningRepository learningResourceRepository;

    public List<LearningResource> getAllLearningResources() {
        return learningResourceRepository.findAll();
    }

    public LearningResource getLearningResourceById(Long id) {
        return learningResourceRepository.findById(id).orElse(null);
    }

    public LearningResource createLearningResource(LearningResource resource) {
        return learningResourceRepository.save(resource);
    }

    public LearningResource updateLearningResource(Long id, LearningResource resourceDetails) {
        LearningResource resource = getLearningResourceById(id);
        if (resource != null) {
            resource.setTitle(resourceDetails.getTitle());
            resource.setDescription(resourceDetails.getDescription());
            resource.setUrl(resourceDetails.getUrl());
            return learningResourceRepository.save(resource);
        }
        return null;
    }

    public void deleteLearningResource(Long id) {
        learningResourceRepository.deleteById(id);
    }
}