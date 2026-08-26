package com.newjoinerportal.content.integration;

import com.newjoinerportal.content.model.Policy;
import com.newjoinerportal.content.repo.PolicyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Full application-context test against a real Postgres container so
 * Flyway migrations (src/main/resources/migration) are exercised for real,
 * not just mocked away -- catches migration/schema-mapping drift that a
 * @WebMvcTest or @DataJpaTest with H2 would miss.
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class PolicyIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private PolicyRepository policyRepository;

    @Test
    void flywayMigrationsRunAndPolicyCanBePersisted() {
        Policy policy = new Policy("Test Policy", "Created by the integration test");

        Policy saved = policyRepository.save(policy);

        assertThat(saved.getId()).isNotNull();
        assertThat(policyRepository.findById(saved.getId())).isPresent();
    }

    @Test
    void seedDataFromMigrationsIsPresent() {
        // V7__seed_policies_data.sql seeds baseline policies on a fresh schema.
        assertThat(policyRepository.count()).isGreaterThan(0);
    }
}
