CREATE TABLE persons (
    id                   BIGSERIAL PRIMARY KEY,
    external_id          BIGINT NOT NULL UNIQUE,
    full_name            VARCHAR(500),
    first_name           VARCHAR(255),
    last_name            VARCHAR(255),
    tmdb_id              INTEGER,
    imdb_id              VARCHAR(50),
    main_profession      VARCHAR(100),
    secondary_profession VARCHAR(100),
    tertiary_profession  VARCHAR(100),
    date_of_birth        VARCHAR(50),
    date_of_death        VARCHAR(50),
    place_of_birth       VARCHAR(500),
    gender               VARCHAR(5),
    headshot_url         VARCHAR(1000),
    known_for            TEXT,
    relevance_percentile DOUBLE PRECISION
);
