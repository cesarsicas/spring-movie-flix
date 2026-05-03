CREATE TABLE title_sources (
    id             BIGSERIAL PRIMARY KEY,
    title_details_id BIGINT NOT NULL REFERENCES title_details(id) ON DELETE CASCADE,
    source_id      INT NOT NULL,
    name           VARCHAR(255),
    type           VARCHAR(50),
    region         VARCHAR(10),
    ios_url        TEXT,
    android_url    TEXT,
    web_url        TEXT,
    format         VARCHAR(10),
    price          NUMERIC(10, 2),
    seasons        INT,
    episodes       INT
);
