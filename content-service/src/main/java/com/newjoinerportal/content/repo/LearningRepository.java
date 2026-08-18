package com.newjoinerportal.content.repo;

import com.newjoinerportal.content.model.LearningResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LearningRepository extends JpaRepository<LearningResource, Long> {
    boolean existsByTitle(String title);
    Optional<LearningResource> findById(Long aLong);
    Optional<LearningResource> findByTitle(String title);

}
