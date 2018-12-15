CREATE TABLE rewards (
    id BIGINT NOT NULL,
    price BIGINT NOT NULL,
    description VARCHAR(300) NOT NULL,
    logo_path VARCHAR(100) NOT NULL,
    amount BIGINT NOT NULL,
    expiration_date DATETIME NOT NULL,

    created_at DATETIME NOT NULL,
    edited_at DATETIME NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE member_rewards (
    id BIGINT NOT NULL,
    price BIGINT NOT NULL,
    description VARCHAR(300) NOT NULL,
    logo_path VARCHAR(100) NOT NULL,
    member_id BIGINT NOT NULL,
    status VARCHAR(100) NOT NULL,

    created_at DATETIME NOT NULL,
    edited_at DATETIME NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES members (id)
);
