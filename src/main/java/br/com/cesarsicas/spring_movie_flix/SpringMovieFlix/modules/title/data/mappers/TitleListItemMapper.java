package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleListItem;

public class TitleListItemMapper {

    public static TitleListItem toDomain(
            br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.TitleListItem remote) {
        if (remote == null) return null;
        return new TitleListItem(
                null,
                remote.id(),
                remote.name(),
                remote.type(),
                remote.year(),
                remote.imdb_id(),
                remote.tmdb_id(),
                remote.tmdb_type()
        );
    }
}
