CREATE TABLE checklist_completions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    checklist_item_id BIGINT NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    completed_at TIMESTAMP
);