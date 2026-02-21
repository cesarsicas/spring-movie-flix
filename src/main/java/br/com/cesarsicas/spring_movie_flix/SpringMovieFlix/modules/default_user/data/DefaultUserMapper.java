package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.data;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.domain.DefaultUser;

public class DefaultUserMapper {

    public static DefaultUser toDomain(DefaultUserEntity entity) {
        if (entity == null) return null;
        var userId = entity.getUser() != null ? entity.getUser().getId() : null;
        return new DefaultUser(
                entity.getId(),
                entity.getName(),
                entity.getBio(),
                userId
        );
    }
}
