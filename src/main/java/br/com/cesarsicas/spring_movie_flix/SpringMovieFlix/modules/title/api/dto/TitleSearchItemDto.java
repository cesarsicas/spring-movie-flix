package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleSearchItem;

public record TitleSearchItemDto(
        Long id,
        String name,
        String type,
        Integer year,
        String imdb_id,
        Integer tmdb_id,
        String tmdb_type
) {
    public TitleSearchItemDto(TitleSearchItem domain) {
        this(domain.id(), domain.name(), domain.type(), domain.year(),
                domain.imdb_id(), domain.tmdb_id(), domain.tmdb_type());
    }
}
