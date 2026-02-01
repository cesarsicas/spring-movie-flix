package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model;
import java.util.List;
import java.util.Optional;

public record TitleDetailsResponse(
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

}