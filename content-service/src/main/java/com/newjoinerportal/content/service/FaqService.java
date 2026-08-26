package com.newjoinerportal.content.service;

import com.newjoinerportal.content.dto.CreateFaqRequest;
import com.newjoinerportal.content.dto.FaqResponse;
import com.newjoinerportal.content.model.Faq;
import com.newjoinerportal.content.repo.FaqRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FaqService {

    private final FaqRepository faqRepository;

    public FaqService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }


    /*
     * =====================================================
     * USER: GET ACTIVE FAQs
     * =====================================================
     */

    public List<FaqResponse> getActiveFaqs() {

        return faqRepository
                .findByActiveTrueOrderByIdAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }


    /*
     * =====================================================
     * ADMIN: CREATE FAQ
     * =====================================================
     */

    public FaqResponse createFaq(
            CreateFaqRequest request
    ) {

        Faq faq = new Faq(
                request.question().trim(),
                request.answer().trim(),
                request.category() == null
                        ? null
                        : request.category().trim()
        );

        Faq savedFaq =
                faqRepository.save(faq);

        return toResponse(savedFaq);
    }


    /*
     * =====================================================
     * ADMIN: REMOVE / DEACTIVATE FAQ
     * =====================================================
     */

    public void deactivateFaq(Long id) {

        Faq faq = faqRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "FAQ not found"
                        )
                );

        faq.setActive(false);

        faqRepository.save(faq);
    }


    /*
     * =====================================================
     * ENTITY -> DTO
     * =====================================================
     */

    private FaqResponse toResponse(Faq faq) {

        return new FaqResponse(
                faq.getId(),
                faq.getQuestion(),
                faq.getAnswer(),
                faq.getCategory()
        );
    }
}