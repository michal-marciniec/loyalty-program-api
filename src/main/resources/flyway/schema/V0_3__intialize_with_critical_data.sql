INSERT INTO permissions(name) VALUES('REGULAR_MEMBER');
INSERT INTO permissions(name) VALUES('MANAGE_OVERTIME');

INSERT INTO bonus_categories(name, permission_id, points_pool, edit_period) VALUES('NO_LIMIT', 1, 100, 1);
INSERT INTO bonus_categories(name, permission_id, points_pool, edit_period) VALUES('OVERTIME', 2, 50, 24);

INSERT INTO roles(name) VALUES('ROLE_MEMBER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');

INSERT INTO roles_permissions(role_id, permission_id) VALUES(1, 1);
INSERT INTO roles_permissions(role_id, permission_id) VALUES(2, 1);
INSERT INTO roles_permissions(role_id, permission_id) VALUES(2, 2);
INSERT INTO roles_permissions(role_id, permission_id) VALUES(3, 1);
INSERT INTO roles_permissions(role_id, permission_id) VALUES(3, 2);

INSERT INTO members(name, avatar_path, email, give_away_pool, gained_points) VALUES('John Doe', 'default-avatar.png', 'john@sample.com', 1000, 1000);
INSERT INTO members_roles(member_id, role_id) VALUES(1, 2);

