package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleListItemEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleListItem;

public class TitleListItemEntityMapper {

    public static TitleListItem toDomain(TitleListItemEntity entity) {
        if (entity == null) return null;
        return new TitleListItem(
                entity.getId(),
                entity.getExternalId(),
                entity.getTitle(),
                entity.getType(),
                entity.getYear(),
                entity.getImdb_id(),
                entity.getTmdb_id(),
                entity.getTmdb_type()
        );
    }

    public static TitleListItemEntity toEntity(TitleListItem domain) {
        if (domain == null) return null;
        var entity = new TitleListItemEntity();
        entity.setExternalId(domain.externalId());
        entity.setTitle(domain.title());
        entity.setType(domain.type());
        entity.setYear(domain.year());
        entity.setImdb_id(domain.imdb_id());
        entity.setTmdb_id(domain.tmdb_id());
        entity.setTmdb_type(domain.tmdb_type());
        return entity;
    }
}
