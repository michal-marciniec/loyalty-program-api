CREATE TABLE badges (
    id BIGINT NOT NULL AUTO_INCREMENT,
    description VARCHAR(300) NOT NULL,
    image_path VARCHAR(500) NOT NULL,

    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    edited_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id)
);

CREATE TABLE members_badges (
  member_id BIGINT NOT NULL,
  badge_id   BIGINT NOT NULL,

  PRIMARY KEY (member_id, badge_id),
  FOREIGN KEY (member_id) REFERENCES members (id) ON DELETE CASCADE,
  FOREIGN KEY (badge_id) REFERENCES badges (id) ON DELETE CASCADE
);