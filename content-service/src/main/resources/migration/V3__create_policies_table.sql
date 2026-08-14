CREATE TABLE IF NOT EXISTS policies (
                                        id BIGSERIAL PRIMARY KEY,
                                        title VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX idx_policies_title ON policies(title);