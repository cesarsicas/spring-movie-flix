package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.chat.api.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatRequestDto(@NotBlank String message) {}
