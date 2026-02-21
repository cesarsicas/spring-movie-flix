package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleSearch;

public class TitleSearchMapper {

    public static TitleSearch toDomain(br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.TitleSearch remote) {
        if (remote == null) return null;
        return new TitleSearch(
                remote.name(),
                remote.relevance(),
                remote.type(),
                remote.id(),
                remote.year(),
                remote.result_type(),
                remote.tmdb_id(),
                remote.tmdb_type(),
                remote.image_url()
        );
    }
}
