package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.TitleDetailsResponse;

import java.util.List;
import java.util.Optional;

public record TitleDetailsDto(
        Long id,
        String title,
        String original_title,
        String plot_overview,
        String type,
        Optional<Integer> runtime_minutes,
        Optional<Integer> year,
        Optional<Integer> end_year,
        Optional<String> release_date,
        Optional<String> imdb_id,
        Optional<Integer> tmdb_id,
        Optional<String> tmdb_type,
        Optional<List<Integer>> genres,
        Optional<List<String>> genre_names,
        Optional<Double> user_rating,
        Optional<Integer> critic_score,
        Optional<String> us_rating,
        Optional<String> poster,
        Optional<String> backdrop,
        Optional<String> original_language,
        Optional<List<Integer>> similar_titles,
        Optional<List<Integer>> networks,
        Optional<List<String>> network_names,
        Optional<String> trailer,
        Optional<String> trailer_thumbnail,
        Optional<Double> relevance_percentile
) {

    public TitleDetailsDto(TitleDetailsResponse detailsResponse) {
        this(
                detailsResponse.id(),
                detailsResponse.title(),
                detailsResponse.original_title(),
                detailsResponse.plot_overview(),
                detailsResponse.type(),
                detailsResponse.runtime_minutes(),
                detailsResponse.year(),
                detailsResponse.end_year(),
                detailsResponse.release_date(),
                detailsResponse.imdb_id(),
                detailsResponse.tmdb_id(),
                detailsResponse.tmdb_type(),
                detailsResponse.genres(),
                detailsResponse.genre_names(),
                detailsResponse.user_rating(),
                detailsResponse.critic_score(),
                detailsResponse.us_rating(),
                detailsResponse.poster(),
                detailsResponse.backdrop(),
                detailsResponse.original_language(),
                detailsResponse.similar_titles(),
                detailsResponse.networks(),
                detailsResponse.network_names(),
                detailsResponse.trailer(),
                detailsResponse.trailer_thumbnail(),
                detailsResponse.relevance_percentile()
        );
    }

}