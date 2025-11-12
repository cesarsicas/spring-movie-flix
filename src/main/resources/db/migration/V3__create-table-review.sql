CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,

    review TEXT NOT NULL,
    datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,

    external_title_id BIGINT NOT NULL,

    default_user_id BIGINT NOT NULL,

    -- Link to the user who wrote the review
    CONSTRAINT fk_default_user
        FOREIGN KEY (default_user_id)
        REFERENCES default_user(id)
);