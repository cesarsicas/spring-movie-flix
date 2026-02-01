package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.ReleaseEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Release;

public class ReleaseMapper {
    public static Release toDomain(ReleaseEntity entity) {
        if (entity == null) return null;
        return new Release(
            entity.getId(),
            entity.getTitle(),
            entity.getType(),
            entity.getImdb_id(),
            entity.getTmdb_id(),
            entity.getTmdb_type(),
            entity.getSeason_number(),
            entity.getPoster_url(),
            entity.getSource_release_date(),
            entity.getSource_id(),
            entity.getSource_name(),
            entity.getIs_original()
        );
    }
}
