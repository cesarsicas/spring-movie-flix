CREATE TABLE transmissions (
    id BIGSERIAL PRIMARY KEY,
    movie_name VARCHAR(255) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    duration INTEGER NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT FALSE
);
