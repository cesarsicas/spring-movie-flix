package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.default_user.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.default_user.data.DefaultUserEntity;

public record DefaultUserDto(long id, String name, String bio) {

    public DefaultUserDto(DefaultUserEntity entity){
        this(entity.getId(), entity.getName(), entity.getBio());
    }
}
