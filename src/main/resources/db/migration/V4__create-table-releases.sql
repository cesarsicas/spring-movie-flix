CREATE TABLE releases (
	id SERIAL PRIMARY KEY,
	title VARCHAR(255) NOT NULL,
	type VARCHAR(50),
	imdb_id VARCHAR(20),
	tmdb_id INTEGER,
	tmdb_type VARCHAR(50),
	season_number INTEGER,
	poster_url TEXT,
	source_release_date VARCHAR(20),
	source_id INTEGER,
	source_name VARCHAR(100),
	is_original INTEGER
);