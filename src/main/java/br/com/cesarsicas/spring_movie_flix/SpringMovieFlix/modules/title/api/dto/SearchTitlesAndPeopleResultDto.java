package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.SearchResult;

import java.util.List;

public record SearchTitlesAndPeopleResultDto(
        List<TitleSearchItemDto> title_results,
        List<PersonSearchItemDto> people_results
) {
    public SearchTitlesAndPeopleResultDto(SearchResult domain) {
        this(
                domain.titleResults().stream().map(TitleSearchItemDto::new).toList(),
                domain.peopleResults().stream().map(PersonSearchItemDto::new).toList()
        );
    }
}
