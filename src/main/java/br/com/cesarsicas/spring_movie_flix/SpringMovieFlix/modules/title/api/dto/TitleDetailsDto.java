package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleDetails;

import java.util.List;
import java.util.Optional;

public record TitleDetailsDto(
        Long id,
        Long externalId,
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


    public TitleDetailsDto(TitleDetails details) {
        this(
                details.id(),
                details.externalId(),
                details.title(),
                details.original_title(),
                details.plot_overview(),
                details.type(),
                details.runtime_minutes(),
                details.year(),
                details.end_year(),
                details.release_date(),
                details.imdb_id(),
                details.tmdb_id(),
                details.tmdb_type(),
                details.genres(),
                details.genre_names(),
                details.user_rating(),
                details.critic_score(),
                details.us_rating(),
                details.poster(),
                details.backdrop(),
                details.original_language(),
                details.similar_titles(),
                details.networks(),
                details.network_names(),
                details.trailer(),
                details.trailer_thumbnail(),
                details.relevance_percentile()
        );
    }

    private static <T> Optional<List<T>> toOptional(List<T> list) {
        return (list == null || list.isEmpty()) ? Optional.empty() : Optional.of(list);
    }

}