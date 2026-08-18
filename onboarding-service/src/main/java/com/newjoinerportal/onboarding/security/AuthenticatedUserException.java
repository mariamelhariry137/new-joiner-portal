package com.newjoinerportal.onboarding.security;

public class AuthenticatedUserException extends RuntimeException {
    public AuthenticatedUserException(String message) {
        super(message);
    }
}