package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain;

public record PersonSearchItem(
        Long id,
        String name,
        String main_profession,
        String imdb_id,
        Integer tmdb_id
) {}
