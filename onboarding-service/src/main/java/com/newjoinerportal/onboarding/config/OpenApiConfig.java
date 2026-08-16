package com.newjoinerportal.onboarding.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI onboardingOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Onboarding Service API")
                .description("Endpoints for onboarding checklist items and user progress tracking.")
                .version("1.0.0"));
    }
}