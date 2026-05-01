package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.SearchResult;

import java.util.List;

public record SearchResultDto(
        List<TitleSearchItemDto> title_results,
        List<PersonSearchItemDto> people_results
) {
    public SearchResultDto(SearchResult domain) {
        this(
                domain.titleResults().stream().map(TitleSearchItemDto::new).toList(),
                domain.peopleResults().stream().map(PersonSearchItemDto::new).toList()
        );
    }
}
