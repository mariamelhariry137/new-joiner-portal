package com.newjoinerportal.content.repo;

import com.newjoinerportal.content.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    boolean existsByTitle(String title);
    Optional<Policy> findByTitle(String title);
}