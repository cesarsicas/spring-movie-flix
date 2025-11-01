package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.movie.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.movie.data.remote.Release;

public record MovieReleasesDto(
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

    public MovieReleasesDto (Release release){
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
}
