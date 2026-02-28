package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleSearch;

public record TitleSearchDto(
        String name,
        double relevance,
        String type,
        int id,
        int year,
        String result_type,
        int tmdb_id,
        String tmdb_type,
        String image_url
){
    public TitleSearchDto(TitleSearch search) {
        this(
                search.name(),
                search.relevance(),
                search.type(),
                search.id(),
                search.year(),
                search.result_type(),
                search.tmdb_id(),
                search.tmdb_type(),
                search.image_url());
    }
}
