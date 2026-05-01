package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model;

import java.util.List;

public record TitleListResponse(
        List<TitleListItem> titles,
        Integer page,
        Integer total_results,
        Integer total_pages
) {}
