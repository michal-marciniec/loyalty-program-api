CREATE TABLE comments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    bonus_id BIGINT NOT NULL,
    body VARCHAR(500) NOT NULL,

    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    edited_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES members (id),
    FOREIGN KEY (bonus_id) REFERENCES bonuses (id)
);