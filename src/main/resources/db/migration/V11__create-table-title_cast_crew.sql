CREATE TABLE title_cast_crew (
    id               BIGSERIAL PRIMARY KEY,
    title_details_id BIGINT NOT NULL REFERENCES title_details(id) ON DELETE CASCADE,
    person_id        BIGINT NOT NULL,
    type             VARCHAR(10),
    full_name        VARCHAR(255),
    headshot_url     TEXT,
    role             TEXT,
    episode_count    INT,
    cast_order       INT
);
