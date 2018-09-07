INSERT INTO members (id, name, avatar_path) VALUES (1, 'admin', 'default-avatar.png');
INSERT INTO members (id, name, avatar_path) VALUES (2, 'member', 'default-avatar.png');
INSERT INTO bonuses (id, points, receiver_id, giver_id, given_at) VALUES (1, 10, 2, 1, CURRENT_TIMESTAMP());