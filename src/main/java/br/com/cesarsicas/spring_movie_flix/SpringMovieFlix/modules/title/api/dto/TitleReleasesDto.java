package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleReleaseEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.ReleaseRemote;

public record TitleReleasesDto(
        long id,
        long external_id,
        String title,
        String type,
        String imdb_id,
        Integer tmdb_id,
        String tmdb_type,
        Integer season_number,
        String poster_url,
        String source_release_date,
        Integer source_id,
        String source_name,
        Integer is_original
){
    public TitleReleasesDto(br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Release release) {
        this(
                release.getId(),
                release.getExternal_id(),
                release.getTitle(),
                release.getType(),
                release.getImdb_id(),
                release.getTmdb_id(),
                release.getTmdb_type(),
                release.getSeason_number(),
                release.getPoster_url(),
                release.getSource_release_date(),
                release.getSource_id(),
                release.getSource_name(),
                release.getIs_original());
    }
}
