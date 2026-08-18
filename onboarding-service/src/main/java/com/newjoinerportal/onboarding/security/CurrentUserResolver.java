package com.newjoinerportal.onboarding.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserResolver {

    private final JwtService jwtService;

    public CurrentUserResolver(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public Long resolve(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new AuthenticatedUserException("Missing or invalid Authorization header");
        }
        String token = header.substring(7);
        try {
            return jwtService.extractUserId(token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticatedUserException("Invalid or expired token");
        }
    }
}