INSERT INTO members (id, email, name, avatar_path, created_at, edited_at) VALUES (1, 'admin@sample.com', 'admin', 'default-avatar.png', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO members (id, email, name, avatar_path, created_at, edited_at) VALUES (2, 'member@sample.com', 'member', 'default-avatar.png', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO members_roles (member_id, role_id) VALUES (1, 1);
INSERT INTO members_roles (member_id, role_id) VALUES (1, 2);
INSERT INTO members_roles (member_id, role_id) VALUES (2, 1);

INSERT INTO permissions (id, name, created_at, edited_at) VALUES (1, 'MANAGE_OVERTIME', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, 1);

INSERT INTO bonus_categories (id, name, permission_id, points_limit, limit_period, edit_period, created_at, edited_at) VALUES (1, 'OVERTIME', 1, 5, 7, 24, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO bonuses (id, points, receiver_id, giver_id, category_id, description, created_at, edited_at) VALUES (1, 10, 2, 1, 1, 'Thanks for staying late', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());