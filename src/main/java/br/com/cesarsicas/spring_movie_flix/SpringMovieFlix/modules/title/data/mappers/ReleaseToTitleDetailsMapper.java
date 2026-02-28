package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Release;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleDetails;

import java.util.Optional;

/**
 * Maps a Release (from cache) to TitleDetails with only the fields that exist on Release.
 * Missing fields are Optional.empty() or null.
 */
public class ReleaseToTitleDetailsMapper {

    public static TitleDetails toTitleDetails(Release release) {
        if (release == null) return null;
        return new TitleDetails(
            null,
            Long.valueOf(release.getExternal_id()),
            release.getTitle(),
            null,
            null,
            release.getType(),
            Optional.<Integer>empty(),
            Optional.<Integer>empty(),
            Optional.<Integer>empty(),
            Optional.ofNullable(release.getSource_release_date()),
            Optional.ofNullable(release.getImdb_id()),
            Optional.ofNullable(release.getTmdb_id()),
            Optional.ofNullable(release.getTmdb_type()),
            Optional.<java.util.List<Integer>>empty(),
            Optional.<java.util.List<String>>empty(),
            Optional.<Double>empty(),
            Optional.<Integer>empty(),
            Optional.<String>empty(),
            Optional.ofNullable(release.getPoster_url()),
            Optional.<String>empty(),
            Optional.<String>empty(),
            Optional.<java.util.List<Integer>>empty(),
            Optional.<java.util.List<Integer>>empty(),
            Optional.<java.util.List<String>>empty(),
            Optional.<String>empty(),
            Optional.<String>empty(),
            Optional.<Double>empty()
        );
    }
}
