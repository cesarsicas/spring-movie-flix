package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.data.TransmissionMovieEntity;

public record TransmissionMovieDto(Long id, String title, Integer duration, String filename) {
    public TransmissionMovieDto(TransmissionMovieEntity e) {
        this(e.getId(), e.getTitle(), e.getDuration(), e.getFilename());
    }
}
