CREATE TABLE badge_condition (
    id BIGINT AUTO_INCREMENT,
    badge_id BIGINT NOT NULL,
    category_id BIGINT DEFAULT NULL,
    condition_type VARCHAR(100) NOT NULL,

    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    edited_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY(id),
    FOREIGN KEY (badge_id) REFERENCES badges (id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES bonus_categories (id) ON DELETE CASCADE
);