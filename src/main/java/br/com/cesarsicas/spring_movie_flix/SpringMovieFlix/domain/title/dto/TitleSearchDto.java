package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.data.remote.model.TitleSearch;

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

    public TitleSearchDto(TitleSearch release){
         this(
                release.name(),
                release.relevance(),
                release.type(),
                release.id(),
                release.year(),
                release.result_type(),
                release.tmdb_id(),
                release.tmdb_type(),
                release.image_url());
    }
}
