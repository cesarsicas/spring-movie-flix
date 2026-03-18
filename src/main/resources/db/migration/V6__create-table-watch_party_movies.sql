CREATE TABLE transmission_movies (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    duration INTEGER NOT NULL,
    filename VARCHAR(255) NOT NULL
);

INSERT INTO transmission_movies (title, duration, filename) VALUES
  ('Inception', 148, 'inception_2010.mp4'),
  ('Interstellar', 169, 'interstellar_2014.mp4');
