package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user_auth.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginDto(
                          @NotEmpty
                          String login, //todo validate email format

                          @NotEmpty
                          String password) {
}