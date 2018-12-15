CREATE TABLE members (
  id          BIGINT AUTO_INCREMENT,
  name        VARCHAR(100) NOT NULL,
  avatar_path VARCHAR(500) NOT NULL,
  email       VARCHAR(100) NOT NULL UNIQUE,
  give_away_pool BIGINT NOT NULL,
  gained_points BIGINT NOT NULL,

  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  edited_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id)
);

CREATE TABLE permissions (
    id BIGINT AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,

    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    edited_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY(id)
);

CREATE TABLE bonus_categories (
  id          BIGINT AUTO_INCREMENT,
  name        VARCHAR(100) NOT NULL UNIQUE,
  permission_id BIGINT NOT NULL,
  points_pool BIGINT,
  edit_period BIGINT NOT NULL,

  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  edited_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id),
  FOREIGN KEY (permission_id) REFERENCES permissions (id)
);

CREATE TABLE bonuses (
  id          BIGINT AUTO_INCREMENT,
  points      BIGINT   NOT NULL,
  giver_id    BIGINT   NOT NULL,
  receiver_id BIGINT   NOT NULL,
  category_id BIGINT   NOT NULL,
  description VARCHAR(300) NOT NULL,

  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  edited_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id),
  FOREIGN KEY (giver_id) REFERENCES members (id),
  FOREIGN KEY (receiver_id) REFERENCES members (id),
  FOREIGN KEY (category_id) REFERENCES bonus_categories (id)
);

CREATE TABLE roles (
  id   BIGINT AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL UNIQUE,

  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  edited_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id)
);

CREATE TABLE members_roles (
  member_id BIGINT NOT NULL,
  role_id   BIGINT NOT NULL,

  PRIMARY KEY (member_id, role_id),
  FOREIGN KEY (member_id) REFERENCES members (id),
  FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE roles_permissions (
  role_id   BIGINT NOT NULL,
  permission_id BIGINT NOT NULL,

  PRIMARY KEY (role_id, permission_id),
  FOREIGN KEY (role_id) REFERENCES roles (id),
  FOREIGN KEY (permission_id) REFERENCES permissions (id)
);
