CREATE TABLE members (
  id          BIGINT AUTO_INCREMENT,
  name        VARCHAR(100) NOT NULL,
  avatar_path VARCHAR(100) NOT NULL,
  email       VARCHAR(100) NOT NULL UNIQUE,

  created_at DATETIME NOT NULL,
  edited_at DATETIME NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE permissions (
    id BIGINT AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,

    created_at DATETIME NOT NULL,
    edited_at DATETIME NOT NULL,

    PRIMARY KEY(id)
);

CREATE TABLE bonus_categories (
  id          BIGINT AUTO_INCREMENT,
  name        VARCHAR(100) NOT NULL UNIQUE,
  permission_id BIGINT NOT NULL,
  points_limit BIGINT NOT NULL,
  limit_period BIGINT NOT NULL,
  edit_period BIGINT NOT NULL,

  created_at DATETIME NOT NULL,
  edited_at DATETIME NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (permission_id) REFERENCES permissions (id)
);

CREATE TABLE bonuses (
  id          BIGINT AUTO_INCREMENT,
  points      BIGINT   NOT NULL,
  giver_id    BIGINT   NOT NULL,
  receiver_id BIGINT   NOT NULL,
  category_id BIGINT   NOT NULL,
  description VARCHAR(300),

  created_at DATETIME NOT NULL,
  edited_at DATETIME NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (giver_id) REFERENCES members (id),
  FOREIGN KEY (receiver_id) REFERENCES members (id),
  FOREIGN KEY (category_id) REFERENCES bonus_categories (id)
);

CREATE TABLE roles (
  id   BIGINT AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL UNIQUE,

  created_at DATETIME NOT NULL,
  edited_at DATETIME NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE members_roles (
  member_id BIGINT NOT NULL,
  role_id   BIGINT NOT NULL,

  FOREIGN KEY (member_id) REFERENCES members (id),
  FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE roles_permissions (
  role_id   BIGINT NOT NULL,
  permission_id BIGINT NOT NULL,

  FOREIGN KEY (role_id) REFERENCES roles (id),
  FOREIGN KEY (permission_id) REFERENCES permissions (id)
);

INSERT INTO roles (id, name, created_at, edited_at) VALUES (1, 'ROLE_MEMBER', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

