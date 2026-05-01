package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain;

import java.util.List;

public record SearchResult(
        List<TitleSearchItem> titleResults,
        List<PersonSearchItem> peopleResults
) {}
