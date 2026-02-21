package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.TitleDetailsResponse;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleDetails;

public class TitleDetailsResponseMapper {

    public static TitleDetails toDomain(TitleDetailsResponse response) {
        if (response == null) return null;
        return new TitleDetails(
                response.id(),
                response.title(),
                response.original_title(),
                response.plot_overview(),
                response.type(),
                response.runtime_minutes(),
                response.year(),
                response.end_year(),
                response.release_date(),
                response.imdb_id(),
                response.tmdb_id(),
                response.tmdb_type(),
                response.genres(),
                response.genre_names(),
                response.user_rating(),
                response.critic_score(),
                response.us_rating(),
                response.poster(),
                response.backdrop(),
                response.original_language(),
                response.similar_titles(),
                response.networks(),
                response.network_names(),
                response.trailer(),
                response.trailer_thumbnail(),
                response.relevance_percentile()
        );
    }
}
