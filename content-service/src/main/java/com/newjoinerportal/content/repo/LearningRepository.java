package com.newjoinerportal.content.repo;

import com.newjoinerportal.content.model.LearningResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningRepository extends JpaRepository<LearningResource, Long> {
}