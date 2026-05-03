package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model;

public record TitleSearchV2(
        Long id,
        String name,
        String type,
        Integer year,
        String imdb_id,
        Integer tmdb_id,
        String tmdb_type
) {}
