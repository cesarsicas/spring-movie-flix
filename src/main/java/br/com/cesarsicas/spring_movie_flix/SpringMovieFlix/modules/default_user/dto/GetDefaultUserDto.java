package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.data.DefaultUserEntity;

public record GetDefaultUserDto(long id, String name, String bio) {

    public GetDefaultUserDto(DefaultUserEntity entity){
        this(entity.getId(), entity.getName(), entity.getBio());
    }
}
