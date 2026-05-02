package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model;

public record AutocompleteSearch(
        String name,
        double relevance,
        String type,
        int id,
        Integer year,
        String result_type,
        String imdb_id,
        Integer tmdb_id,
        String tmdb_type,
        String image_url
) {
}