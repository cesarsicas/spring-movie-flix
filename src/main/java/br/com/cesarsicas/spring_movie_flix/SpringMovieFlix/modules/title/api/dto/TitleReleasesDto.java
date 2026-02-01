package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.ReleaseEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.Release;

public record TitleReleasesDto(
        int id,
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

    public TitleReleasesDto(Release release){
         this(
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
                release.is_original());
    }

    public TitleReleasesDto(ReleaseEntity releaseEntity){
        this(
                releaseEntity.getId(),
                releaseEntity.getTitle(),
                releaseEntity.getType(),
                releaseEntity.getImdb_id(),
                releaseEntity.getTmdb_id(),
                releaseEntity.getTmdb_type(),
                releaseEntity.getSeason_number(),
                releaseEntity.getPoster_url(),
                releaseEntity.getSource_release_date(),
                releaseEntity.getSource_id(),
                releaseEntity.getSource_name(),
                releaseEntity.getIs_original());
    }
}
