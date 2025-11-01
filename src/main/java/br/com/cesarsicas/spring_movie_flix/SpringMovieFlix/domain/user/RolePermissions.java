package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RolePermissions {
    ADMIN_CREATE("admin:create"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),

    DEFAULT_CREATE("default:create"),
    DEFAULT_READ("default:read"),
    DEFAULT_UPDATE("default:update"),
    DEFAULT_DELETE("default:delete");


    @Getter
    private final String permissions;
}
