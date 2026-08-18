package com.newjoinerportal.content.service;

import com.newjoinerportal.content.exception.DataIntegrityException;
import com.newjoinerportal.content.exception.DuplicateResource;
import com.newjoinerportal.content.exception.ResourceNotFound;
import com.newjoinerportal.content.model.LearningResource;
import com.newjoinerportal.content.model.Team;
import com.newjoinerportal.content.repo.LearningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LearningService {

    @Autowired
    private LearningRepository learningResourceRepository;

    public List<LearningResource> getAllLearningResources() {
        return learningResourceRepository.findAll();
    }

    public LearningResource getLearningResourceById(Long id) {
        return learningResourceRepository.findById(id).orElseThrow(()-> new ResourceNotFound("LearningResource", id));
    }

    @Transactional
    public LearningResource createLearningResource(LearningResource resource) {
        if(learningResourceRepository.findByTitle(resource.getTitle()).isPresent()){
            throw new DuplicateResource("Learning resource with title" + resource.getTitle()+"already exists");
        }
        try{
            return learningResourceRepository.save(resource);
        }catch (Exception e){
            throw new DataIntegrityException("Failed to create learning resource" + e.getMessage());
        }
    }

    public LearningResource updateLearningResource(Long id, LearningResource resourceDetails) {
        LearningResource resource = getLearningResourceById(id);
        if(learningResourceRepository.findByTitle(resourceDetails.getTitle()).isPresent()
                && !resource.getTitle().equals(resourceDetails.getTitle())){
            throw new DuplicateResource("Learning resource with title"+resourceDetails.getTitle()+"already exists");
        }
        resource.setTitle(resourceDetails.getTitle());
        resource.setDescription(resourceDetails.getDescription());
        resource.setUrl(resourceDetails.getUrl());
        try{
            return learningResourceRepository.save(resource);
        }catch (Exception e){
            throw new DataIntegrityException("failed to update learning resource: "+ e.getMessage());
        }
    }

    @Transactional
    public void deleteLearningResource(Long id) {
        LearningResource resource = getLearningResourceById(id);
        try{
            learningResourceRepository.delete(resource);
        } catch (Exception e) {
            throw new DataIntegrityException("Failed to delete learning resource : "+e.getMessage());
        }
    }
}
