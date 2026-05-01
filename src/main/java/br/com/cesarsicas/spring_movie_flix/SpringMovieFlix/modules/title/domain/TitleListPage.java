package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain;

import java.util.List;

public record TitleListPage(
        List<TitleListItem> titles,
        Integer page,
        Integer totalResults,
        Integer totalPages
) {}
