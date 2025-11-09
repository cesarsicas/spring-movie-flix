CREATE TABLE default_user (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    bio VARCHAR(200),
    user_id BIGINT,
    CONSTRAINT fk_default_users_id FOREIGN KEY (user_id) REFERENCES users(id)
);