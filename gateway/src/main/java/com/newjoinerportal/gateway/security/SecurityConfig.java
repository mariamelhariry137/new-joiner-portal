package com.newjoinerportal.gateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.secret}")
    private String jwtSecret;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(
            ServerHttpSecurity http
    ) {

        http
                // REST API -> CSRF is not needed here
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // Enable CORS using the configuration below
                .cors(cors ->
                        cors.configurationSource(corsConfigurationSource())
                )

                .authorizeExchange(exchanges -> exchanges

                        // IMPORTANT:
                        // Allow browser CORS preflight requests
                        .pathMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()

                        // Public endpoints
                        .pathMatchers(
                                "/actuator/health",
                                "/actuator/info",
                                "/actuator/gateway",
                                "/fallback/**",
                                "/api/auth/register",
                                "/api/auth/login",
                                "/api/auth/refresh-token"
                        )
                        .permitAll()

                        // Everything else requires authentication
                        .anyExchange()
                        .authenticated()
                )

                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtDecoder(reactiveJwtDecoder())
                        )
                );

        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        byte[] secretBytes;

        try {
            secretBytes = Base64.getDecoder().decode(jwtSecret);
        } catch (IllegalArgumentException e) {
            secretBytes = jwtSecret.getBytes();
        }

        SecretKeySpec secretKey =
                new SecretKeySpec(secretBytes, "HmacSHA256");

        return NimbusReactiveJwtDecoder
                .withSecretKey(secretKey)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        // Frontend origin
        configuration.setAllowedOrigins(
                List.of("http://localhost:3000", "http://localhost:3001")
        );

        // HTTP methods used by the frontend
        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "PATCH",
                        "DELETE",
                        "OPTIONS"
                )
        );

        // Allow headers such as Content-Type and Authorization
        configuration.setAllowedHeaders(
                List.of("*")
        );

        // Let frontend read Authorization header if needed
        configuration.setExposedHeaders(
                List.of("Authorization")
        );

        // Cache browser preflight for 1 hour
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}