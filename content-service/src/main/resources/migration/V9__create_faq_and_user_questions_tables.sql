-- =========================================================
-- Frequently Asked Questions
-- =========================================================

CREATE TABLE faqs (
                      id BIGSERIAL PRIMARY KEY,

                      question VARCHAR(500) NOT NULL,

                      answer TEXT NOT NULL,

                      category VARCHAR(100),

                      active BOOLEAN NOT NULL DEFAULT TRUE,

                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                      updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- =========================================================
-- User-specific questions sent to the admin
-- =========================================================

CREATE TABLE user_questions (
                                id BIGSERIAL PRIMARY KEY,

                                user_id BIGINT NOT NULL,

                                question TEXT NOT NULL,

                                status VARCHAR(20) NOT NULL DEFAULT 'PENDING',

                                admin_response TEXT,

                                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                answered_at TIMESTAMP,

                                CONSTRAINT chk_user_question_status
                                    CHECK (status IN ('PENDING', 'ANSWERED'))
);


-- =========================================================
-- Indexes
-- =========================================================

CREATE INDEX idx_user_questions_user_id
    ON user_questions(user_id);

CREATE INDEX idx_user_questions_status
    ON user_questions(status);

CREATE INDEX idx_faqs_active
    ON faqs(active);