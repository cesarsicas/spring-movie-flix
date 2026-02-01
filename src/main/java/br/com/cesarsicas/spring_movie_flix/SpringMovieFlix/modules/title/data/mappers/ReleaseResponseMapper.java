package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;
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

    public static Release toDomain(br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.Release release) {
        if (release == null) return null;
        return new Release(
            release.id(),
            release.title(),
            release.type(),
            release.imdb_id(),
            release.tmdb_id(),
            release.tmdb_type(),
            release.season_number(),
            release.poster_url(),
            release.source_release_date(),
            release.source_id(),
            release.source_name(),
            release.is_original()
        );
    }
}
