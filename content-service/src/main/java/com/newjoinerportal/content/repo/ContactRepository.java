package com.newjoinerportal.content.repo;

import com.newjoinerportal.content.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByTeamId(Long teamId);
    boolean existsByEmail(String email);
    boolean existsByTeamId(Long teamId);
    Optional<Contact> findByEmail(String email);
}
