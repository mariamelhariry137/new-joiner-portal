CREATE TABLE checklist_items (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(500) NOT NULL DEFAULT '',
    order_index INTEGER NOT NULL
);