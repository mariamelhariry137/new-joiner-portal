CREATE TABLE IF NOT EXISTS learning_resources (
                                                  id BIGSERIAL PRIMARY KEY,
                                                  title VARCHAR(255) NOT NULL,
    description TEXT,
    url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_learning_resources_title ON learning_resources(title);