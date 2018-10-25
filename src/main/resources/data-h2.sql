INSERT INTO members (id, email, name, avatar_path) VALUES (1, 'admin@sample.com', 'admin', 'default-avatar.png');
INSERT INTO members (id, email, name, avatar_path) VALUES (2, 'member@sample.com', 'member', 'default-avatar.png');
INSERT INTO bonuses (id, points, receiver_id, giver_id, given_at) VALUES (1, 10, 2, 1, CURRENT_TIMESTAMP());

INSERT INTO roles (id, name) VALUES (1, 'ROLE_MEMBER');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO members_roles (member_id, role_id) VALUES (1, 1);
INSERT INTO members_roles (member_id, role_id) VALUES (1, 2);
INSERT INTO members_roles (member_id, role_id) VALUES (2, 1);

INSERT INTO permissions (id, name) VALUES (1, 'OVERTIME_MANAGER');
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, 1);

INSERT INTO bonus_categories (id, name, permission_id, points_limit, limit_period) VALUES (1, 'OVERTIME', 1, 5, 7);

INSERT INTO bonuses (id, points, receiver_id, giver_id, given_at, category_id) VALUES (1, 10, 2, 1, CURRENT_TIMESTAMP(), 1);