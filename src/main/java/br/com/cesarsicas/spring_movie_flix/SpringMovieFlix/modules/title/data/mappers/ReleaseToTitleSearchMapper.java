package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Release;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleSearch;

/**
 * Maps a Release (from cache) to TitleSearch for search results.
 */
public class ReleaseToTitleSearchMapper {

    public static TitleSearch toTitleSearch(Release release) {
        if (release == null) return null;
        return new TitleSearch(
                release.getTitle() != null ? release.getTitle() : "",
                1.0,
                release.getType() != null ? release.getType() : "",
                (int) release.getExternal_id(),
                0,
                release.getType() != null ? release.getType() : "",
                release.getTmdb_id() != null ? release.getTmdb_id() : 0,
                release.getTmdb_type() != null ? release.getTmdb_type() : "",
                release.getPoster_url() != null ? release.getPoster_url() : ""
        );
    }
}
