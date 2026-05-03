package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.PersonSearchItem;

public record PersonSearchItemDto(
        Long id,
        String name,
        String main_profession,
        String imdb_id,
        Integer tmdb_id
) {
    public PersonSearchItemDto(PersonSearchItem domain) {
        this(domain.id(), domain.name(), domain.main_profession(),
                domain.imdb_id(), domain.tmdb_id());
    }
}
