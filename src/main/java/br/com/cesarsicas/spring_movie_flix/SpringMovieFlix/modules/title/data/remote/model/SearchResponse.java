package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model;

import java.util.List;

public record SearchResponse(
        List<TitleSearchV2> title_results,
        List<PersonSearchResult> people_results
) {}
