package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain;

public record Genre(
        Long id,
        int externalId,
        String name,
        int tmdb_id
) {}
