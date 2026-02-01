package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model;

import java.util.List;

public record TitleSearchResponse(
        List<TitleSearch> results
) {}