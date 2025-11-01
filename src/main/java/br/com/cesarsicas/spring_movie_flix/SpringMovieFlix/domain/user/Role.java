package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    DEFAULT(Set.of(
            RolePermissions.DEFAULT_CREATE,
            RolePermissions.DEFAULT_READ,
            RolePermissions.DEFAULT_UPDATE,
            RolePermissions.DEFAULT_DELETE
    )),

    ADMIN(Set.of(
            RolePermissions.ADMIN_CREATE,
            RolePermissions.ADMIN_READ,
            RolePermissions.ADMIN_UPDATE,
            RolePermissions.ADMIN_DELETE));

    @Getter
    private final Set<RolePermissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream().map(permission -> new SimpleGrantedAuthority(permission.getPermissions()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

        return authorities;
    }

}
