package com.newjoinerportal.onboarding.service;

public class ChecklistItemNotFoundException extends RuntimeException {
    public ChecklistItemNotFoundException(String message) {
        super(message);
    }
}