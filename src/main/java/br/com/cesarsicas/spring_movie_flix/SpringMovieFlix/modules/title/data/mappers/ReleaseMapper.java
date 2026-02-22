package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleReleaseEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Release;

public class ReleaseMapper {
    public static Release toDomain(TitleReleaseEntity entity) {
        if (entity == null) return null;
        var release = new Release(
            entity.getExternal_id(),  // external_id (API id), not entity.getId()
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
        release.setId(entity.getId());
        return release;
    }
}
