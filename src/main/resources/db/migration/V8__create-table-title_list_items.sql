CREATE TABLE title_list_items (
    id          BIGSERIAL PRIMARY KEY,
    external_id BIGINT NOT NULL UNIQUE,
    title       VARCHAR(500),
    type        VARCHAR(50),
    year        INTEGER,
    imdb_id     VARCHAR(50),
    tmdb_id     INTEGER,
    tmdb_type   VARCHAR(50)
);
