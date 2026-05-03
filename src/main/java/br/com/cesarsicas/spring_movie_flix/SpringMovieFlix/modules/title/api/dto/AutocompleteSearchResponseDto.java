package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.AutocompleteSearchResponse;

import java.util.List;

public record AutocompleteSearchResponseDto(
        List<AutocompleteSearchItemDto> results
) {
    public AutocompleteSearchResponseDto(AutocompleteSearchResponse remote) {
        this(remote.results().stream().map(AutocompleteSearchItemDto::new).toList());
    }
}
