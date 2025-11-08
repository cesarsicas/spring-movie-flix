package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.data.remote.model;

public record Release(
        int id,
        String title,
        String type,
        String imdb_id,
        Integer tmdb_id,
        String tmdb_type,
        Integer season_number,
        String poster_url,
        String source_release_date,
        Integer source_id,
        String source_name,
        Integer is_original
) {}