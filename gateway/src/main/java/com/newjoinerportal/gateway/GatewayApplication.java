package com.newjoinerportal.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    // Optional: Alternative routing configuration in Java
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r
                        .path("/api/auth/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("authCB")
                                        .setFallbackUri("forward:/fallback/auth"))
                                .retry(config -> config
                                        .setRetries(3)
                                        .setStatuses(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.BAD_GATEWAY)))
                        .uri("http://auth-service:8081"))
                .route("onboarding-service", r -> r
                        .path("/api/onboarding/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("onboardingCB")
                                        .setFallbackUri("forward:/fallback/onboarding")))
                        .uri("http://onboarding-service:8082"))
                .route("content-service", r -> r
                        .path("/api/content/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("contentCB")
                                        .setFallbackUri("forward:/fallback/content")))
                        .uri("http://content-service:8083"))
                .build();
    }
}