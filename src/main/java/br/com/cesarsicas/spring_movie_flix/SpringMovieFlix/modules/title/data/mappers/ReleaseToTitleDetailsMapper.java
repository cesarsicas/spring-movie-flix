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
                release.getExternal_id(),
                release.getTitle(),
                null,
                null,
                release.getType(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.ofNullable(release.getSource_release_date()),
                Optional.ofNullable(release.getImdb_id()),
                Optional.ofNullable(release.getTmdb_id()),
                Optional.ofNullable(release.getTmdb_type()),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.ofNullable(release.getPoster_url()),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
    }
}
