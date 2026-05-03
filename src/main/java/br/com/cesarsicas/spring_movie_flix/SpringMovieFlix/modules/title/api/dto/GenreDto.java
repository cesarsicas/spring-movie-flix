package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Genre;

public record GenreDto(
        Long id,
        int externalId,
        String name,
        int tmdb_id
) {
    public GenreDto(Genre domain) {
        this(domain.id(), domain.externalId(), domain.name(), domain.tmdb_id());
    }
}
