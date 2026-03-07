package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.api.dto;

import jakarta.validation.constraints.NotNull;

public record CreateTransmissionDto(@NotNull Long movieId) {}
