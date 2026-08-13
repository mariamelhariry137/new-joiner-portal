CREATE TABLE IF NOT EXISTS contacts (
                                        id BIGSERIAL PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(50),
    team_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_contacts_team FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE SET NULL
    );

CREATE INDEX idx_contacts_email ON contacts(email);
CREATE INDEX idx_contacts_team_id ON contacts(team_id);