package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleListPage;

import java.util.List;

public record TitleListResponseDto(
        List<TitleListItemDto> titles,
        Integer page,
        Integer total_results,
        Integer total_pages
) {
    public TitleListResponseDto(TitleListPage page) {
        this(
                page.titles().stream().map(TitleListItemDto::new).toList(),
                page.page(),
                page.totalResults(),
                page.totalPages()
        );
    }
}
