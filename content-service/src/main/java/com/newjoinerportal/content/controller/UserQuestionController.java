package com.newjoinerportal.content.controller;

import com.newjoinerportal.content.dto.AnswerQuestionRequest;
import com.newjoinerportal.content.dto.AskQuestionRequest;
import com.newjoinerportal.content.dto.UserQuestionResponse;
import com.newjoinerportal.content.service.UserQuestionService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserQuestionController {

    private final UserQuestionService userQuestionService;

    public UserQuestionController(
            UserQuestionService userQuestionService
    ) {
        this.userQuestionService =
                userQuestionService;
    }


    /*
     * =====================================================
     * USER: ASK A QUESTION
     * =====================================================
     */

    @PostMapping("/questions")
    public ResponseEntity<UserQuestionResponse> askQuestion(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody AskQuestionRequest request
    ) {

        Long userId = getUserId(jwt);

        UserQuestionResponse question =
                userQuestionService.askQuestion(
                        userId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(question);
    }


    /*
     * =====================================================
     * USER: GET ONLY MY QUESTIONS
     * =====================================================
     */

    @GetMapping("/questions/me")
    public ResponseEntity<List<UserQuestionResponse>>
    getMyQuestions(
            @AuthenticationPrincipal Jwt jwt
    ) {

        Long userId = getUserId(jwt);

        return ResponseEntity.ok(
                userQuestionService
                        .getMyQuestions(userId)
        );
    }


    /*
     * =====================================================
     * TEMPORARY ADMIN: GET ALL USER QUESTIONS
     * =====================================================
     */

    @GetMapping("/admin/questions")
    public ResponseEntity<List<UserQuestionResponse>>
    getAllQuestions() {

        return ResponseEntity.ok(
                userQuestionService
                        .getAllQuestions()
        );
    }


    /*
     * =====================================================
     * TEMPORARY ADMIN: ANSWER A QUESTION
     * =====================================================
     */

    @PutMapping("/admin/questions/{id}/answer")
    public ResponseEntity<UserQuestionResponse>
    answerQuestion(
            @PathVariable Long id,
            @Valid @RequestBody
            AnswerQuestionRequest request
    ) {

        return ResponseEntity.ok(
                userQuestionService
                        .answerQuestion(
                                id,
                                request
                        )
        );
    }


    /*
     * =====================================================
     * GET USER ID FROM JWT
     * =====================================================
     */

    private Long getUserId(Jwt jwt) {

        Object userIdClaim =
                jwt.getClaim("userId");

        if (userIdClaim == null) {
            throw new IllegalStateException(
                    "Authenticated token does not contain userId"
            );
        }

        return Long.valueOf(
                userIdClaim.toString()
        );
    }
}