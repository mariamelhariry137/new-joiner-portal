package com.newjoinerportal.content.service;

import com.newjoinerportal.content.dto.AnswerQuestionRequest;
import com.newjoinerportal.content.dto.AskQuestionRequest;
import com.newjoinerportal.content.dto.UserQuestionResponse;
import com.newjoinerportal.content.model.UserQuestion;
import com.newjoinerportal.content.repo.UserQuestionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserQuestionService {

    private final UserQuestionRepository userQuestionRepository;

    public UserQuestionService(
            UserQuestionRepository userQuestionRepository
    ) {
        this.userQuestionRepository =
                userQuestionRepository;
    }


    /*
     * =====================================================
     * USER: SUBMIT QUESTION
     * =====================================================
     */

    public UserQuestionResponse askQuestion(
            Long userId,
            AskQuestionRequest request
    ) {

        UserQuestion question =
                new UserQuestion(
                        userId,
                        request.question().trim()
                );

        UserQuestion savedQuestion =
                userQuestionRepository.save(question);

        return toResponse(savedQuestion);
    }


    /*
     * =====================================================
     * USER: GET MY QUESTIONS
     * =====================================================
     */

    public List<UserQuestionResponse> getMyQuestions(
            Long userId
    ) {

        return userQuestionRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }


    /*
     * =====================================================
     * ADMIN: GET ALL USER QUESTIONS
     * =====================================================
     */

    public List<UserQuestionResponse> getAllQuestions() {

        return userQuestionRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }


    /*
     * =====================================================
     * ADMIN: ANSWER USER QUESTION
     * =====================================================
     */

    public UserQuestionResponse answerQuestion(
            Long questionId,
            AnswerQuestionRequest request
    ) {

        UserQuestion question =
                userQuestionRepository
                        .findById(questionId)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Question not found"
                                )
                        );

        question.answer(
                request.answer().trim()
        );

        UserQuestion savedQuestion =
                userQuestionRepository.save(question);

        return toResponse(savedQuestion);
    }


    /*
     * =====================================================
     * ENTITY -> DTO
     * =====================================================
     */

    private UserQuestionResponse toResponse(
            UserQuestion question
    ) {

        return new UserQuestionResponse(
                question.getId(),
                question.getQuestion(),
                question.getStatus(),
                question.getAdminResponse(),
                question.getCreatedAt(),
                question.getAnsweredAt()
        );
    }
}