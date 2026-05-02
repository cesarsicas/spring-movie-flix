CREATE TABLE genres (
    id          BIGSERIAL PRIMARY KEY,
    external_id INT NOT NULL UNIQUE,
    name        VARCHAR(100) NOT NULL,
    tmdb_id     INT NOT NULL
);
