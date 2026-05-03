package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.CastCrewMemberRemote;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.TitleDetailsResponse;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.TitleSourceRemote;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.CastCrewMember;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleDetails;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleSource;
import java.util.List;
import java.util.Optional;

public class TitleDetailsResponseMapper {

    public static TitleDetails toDomain(TitleDetailsResponse response) {
        if (response == null) return null;
        return new TitleDetails(
            null,
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
            response.relevance_percentile(),
            response.sources().map(list -> list.stream().map(TitleDetailsResponseMapper::toSource).toList()),
            response.cast().map(list -> list.stream().map(TitleDetailsResponseMapper::toCastCrewMember).toList())
        );
    }

    private static TitleSource toSource(TitleSourceRemote r) {
        return new TitleSource(r.source_id(), r.name(), r.type(), r.region(),
                r.ios_url(), r.android_url(), r.web_url(), r.format(), r.price(), r.seasons(), r.episodes());
    }

    private static CastCrewMember toCastCrewMember(CastCrewMemberRemote r) {
        return new CastCrewMember(r.person_id(), r.type(), r.full_name(),
                r.headshot_url(), r.role(), r.episode_count(), r.order());
    }
}
