package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.ReleaseRemote;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.ReleaseResponse;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Release;

import java.util.List;
import java.util.stream.Collectors;

public class ReleaseResponseMapper {
    public static List<Release> toDomainList(ReleaseResponse response) {
        if (response == null || response.releases() == null) return List.of();
        return response.releases().stream()
                .map(ReleaseResponseMapper::toDomain)
                .collect(Collectors.toList());
    }

    public static Release toDomain(ReleaseRemote releaseRemote) {
        if (releaseRemote == null) return null;
        // API id is the external id; domain id (local/DB) is not set when mapping from remote
        return new Release(
            releaseRemote.id(),  // -> domain.external_id
            releaseRemote.title(),
            releaseRemote.type(),
            releaseRemote.imdb_id(),
            releaseRemote.tmdb_id(),
            releaseRemote.tmdb_type(),
            releaseRemote.season_number(),
            releaseRemote.poster_url(),
            releaseRemote.source_release_date(),
            releaseRemote.source_id(),
            releaseRemote.source_name(),
            releaseRemote.is_original()
        );
    }
}
