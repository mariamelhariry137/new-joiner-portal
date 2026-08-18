package com.newjoinerportal.content.repo;

import com.newjoinerportal.content.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsByName(String name);
    boolean existsById(int id);

    Optional<Team> findByName(String name);
}
