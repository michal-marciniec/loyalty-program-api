INSERT INTO members (id, email, name, avatar_path) VALUES (1, 'admin@sample.com', 'admin', 'default-avatar.png');
INSERT INTO members (id, email, name, avatar_path) VALUES (2, 'member@sample.com', 'member', 'default-avatar.png');
INSERT INTO bonuses (id, points, receiver_id, giver_id, given_at) VALUES (1, 10, 2, 1, CURRENT_TIMESTAMP());

INSERT INTO roles (id, name) VALUES (1, 'ROLE_MEMBER');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO members_roles (member_id, role_id) VALUES (1, 1);
INSERT INTO members_roles (member_id, role_id) VALUES (1, 2);
INSERT INTO members_roles (member_id, role_id) VALUES (2, 1);