package com.newjoinerportal.gateway.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Reactive web-layer test for the gateway's circuit-breaker fallback
 * endpoints -- these are the only routes GatewayController owns directly,
 * the rest of the gateway's behavior is declarative routing config.
 * Reactive security auto-configuration is excluded because the gateway's
 * real SecurityConfig (JWT resource server) isn't part of this slice, and
 * Spring's default reactive security otherwise demands an AuthenticationManager.
 */
@WebFluxTest(GatewayController.class)
@AutoConfigureWebTestClient
@ImportAutoConfiguration(exclude = {
        ReactiveSecurityAutoConfiguration.class,
        ReactiveUserDetailsServiceAutoConfiguration.class,
        ReactiveOAuth2ResourceServerAutoConfiguration.class
})
class GatewayControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void fallback_returnsServiceUnavailable_withServiceName() {
        webTestClient.get().uri("/fallback/content-service")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.status").isEqualTo("ERROR")
                .jsonPath("$.message").isEqualTo(
                        "content-service service is currently unavailable. Please try again later.");
    }

    @Test
    void authFallback_returnsServiceUnavailable() {
        webTestClient.get().uri("/fallback/auth")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.status").isEqualTo("ERROR")
                .jsonPath("$.message").isEqualTo("Authentication service is temporarily unavailable");
    }
}
