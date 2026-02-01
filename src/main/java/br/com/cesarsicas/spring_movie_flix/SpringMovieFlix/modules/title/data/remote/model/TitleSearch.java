package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model;

public record TitleSearch(
        String name,
        double relevance,
        String type,
        int id,
        int year,
        String result_type,
        int tmdb_id,
        String tmdb_type,
        String image_url
) {
}