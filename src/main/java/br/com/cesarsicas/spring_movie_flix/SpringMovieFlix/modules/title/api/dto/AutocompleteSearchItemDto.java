package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.AutocompleteSearch;

public record AutocompleteSearchItemDto(
        String name,
        double relevance,
        String type,
        int id,
        Integer year,
        String result_type,
        String imdb_id,
        Integer tmdb_id,
        String tmdb_type,
        String image_url
) {
    public AutocompleteSearchItemDto(AutocompleteSearch remote) {
        this(
                remote.name(),
                remote.relevance(),
                remote.type(),
                remote.id(),
                remote.year(),
                remote.result_type(),
                remote.imdb_id(),
                remote.tmdb_id(),
                remote.tmdb_type(),
                remote.image_url()
        );
    }
}