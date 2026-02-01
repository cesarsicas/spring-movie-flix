package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user_auth.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.Role;
import jakarta.validation.constraints.NotEmpty;

public record RegisterDto(
        String login,
        @NotEmpty
        String password,
        Role role) {
}
