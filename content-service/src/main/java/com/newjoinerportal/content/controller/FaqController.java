package com.newjoinerportal.content.controller;

import com.newjoinerportal.content.dto.CreateFaqRequest;
import com.newjoinerportal.content.dto.FaqResponse;
import com.newjoinerportal.content.service.FaqService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FaqController {

    private final FaqService faqService;

    public FaqController(FaqService faqService) {
        this.faqService = faqService;
    }


    /*
     * =====================================================
     * USER: GET ALL ACTIVE FAQs
     * =====================================================
     */

    @GetMapping("/faqs")
    public ResponseEntity<List<FaqResponse>> getFaqs() {

        return ResponseEntity.ok(
                faqService.getActiveFaqs()
        );
    }


    /*
     * =====================================================
     * TEMPORARY ADMIN: CREATE FAQ
     * =====================================================
     */

    @PostMapping("/admin/faqs")
    public ResponseEntity<FaqResponse> createFaq(
            @Valid @RequestBody CreateFaqRequest request
    ) {

        FaqResponse faq =
                faqService.createFaq(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(faq);
    }


    /*
     * =====================================================
     * TEMPORARY ADMIN: REMOVE FAQ FROM USER VIEW
     * =====================================================
     */

    @DeleteMapping("/admin/faqs/{id}")
    public ResponseEntity<Void> deactivateFaq(

              @PathVariable Long id
    ) {

        faqService.deactivateFaq(id);

        return ResponseEntity.noContent().build();
    }
}