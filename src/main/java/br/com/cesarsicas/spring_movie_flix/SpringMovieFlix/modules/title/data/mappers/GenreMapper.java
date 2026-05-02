package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.GenreEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.GenreRemote;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Genre;

public class GenreMapper {

    public static Genre toDomain(GenreRemote remote) {
        return new Genre(null, remote.id(), remote.name(), remote.tmdb_id());
    }

    public static Genre toDomain(GenreEntity entity) {
        return new Genre(entity.getId(), entity.getExternalId(), entity.getName(), entity.getTmdb_id());
    }

    public static GenreEntity toEntity(Genre domain) {
        var entity = new GenreEntity();
        entity.setExternalId(domain.externalId());
        entity.setName(domain.name());
        entity.setTmdb_id(domain.tmdb_id());
        return entity;
    }
}
