package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model;

public record GenreRemote(
        int id,
        String name,
        int tmdb_id
) {}
