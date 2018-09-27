CREATE TABLE members (
  id          BIGINT AUTO_INCREMENT,
  name        VARCHAR(100) NOT NULL,
  avatar_path VARCHAR(100) NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE bonuses (
  id          BIGINT AUTO_INCREMENT,
  points      INT      NOT NULL,
  giver_id    BIGINT   NOT NULL,
  receiver_id BIGINT   NOT NULL,
  given_at    DATETIME NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (giver_id) REFERENCES members (id),
  FOREIGN KEY (receiver_id) REFERENCES members (id)
);