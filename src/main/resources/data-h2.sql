INSERT INTO members (id, email, name, avatar_path, created_at, edited_at, give_away_pool, gained_points)
VALUES (1, 'admin@sample.com', 'admin', 'default-avatar.png', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 10, 0);

INSERT INTO members (id, email, name, avatar_path, created_at, edited_at, give_away_pool, gained_points)
VALUES (2, 'member@sample.com', 'member', 'default-avatar.png', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 10, 0);

INSERT INTO roles (id, name, created_at, edited_at) VALUES (1, 'ROLE_MEMBER', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO roles (id, name, created_at, edited_at) VALUES (2, 'ROLE_ADMIN', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO roles (id, name, created_at, edited_at) VALUES (3, 'ROLE_MODERATOR', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO members_roles (member_id, role_id) VALUES (1, 1);
INSERT INTO members_roles (member_id, role_id) VALUES (1, 2);
INSERT INTO members_roles (member_id, role_id) VALUES (1, 3);
INSERT INTO members_roles (member_id, role_id) VALUES (2, 1);

INSERT INTO permissions (id, name, created_at, edited_at) VALUES (1, 'MANAGE_OVERTIME', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO permissions (id, name, created_at, edited_at) VALUES (2, 'REGULAR_MEMBER', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO roles_permissions (role_id, permission_id) VALUES (3, 1);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, 2);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (2, 2);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (3, 2);

INSERT INTO bonus_categories (id, name, permission_id, points_pool, edit_period, created_at, edited_at)
VALUES (1, 'OVERTIME', 1, 10, 24, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO bonus_categories (id, name, permission_id, points_pool, edit_period, created_at, edited_at)
VALUES (2, 'NO_LIMIT', 2, NULL, 8, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());