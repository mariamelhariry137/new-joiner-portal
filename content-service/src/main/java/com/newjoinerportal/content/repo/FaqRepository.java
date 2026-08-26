package com.newjoinerportal.content.repo;

import com.newjoinerportal.content.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository
        extends JpaRepository<Faq, Long> {

    List<Faq> findByActiveTrueOrderByIdAsc();
}