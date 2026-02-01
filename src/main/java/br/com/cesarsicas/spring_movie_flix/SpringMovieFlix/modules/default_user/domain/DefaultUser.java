package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DefaultUser {
    private Long id;
    private String name;
    private String bio;
    private Long userId;

    public DefaultUser() {}

    public DefaultUser(Long id, String name, String bio, Long userId) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.userId = userId;
    }
}
