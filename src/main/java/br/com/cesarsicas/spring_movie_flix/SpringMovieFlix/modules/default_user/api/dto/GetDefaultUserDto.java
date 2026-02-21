package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.data.DefaultUserEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.domain.DefaultUser;

public record GetDefaultUserDto(long id, String name, String bio) {

    public GetDefaultUserDto(DefaultUserEntity entity) {
        this(entity.getId(), entity.getName(), entity.getBio());
    }

    public GetDefaultUserDto(DefaultUser defaultUser) {
        this(defaultUser.getId(), defaultUser.getName(), defaultUser.getBio());
    }
}
