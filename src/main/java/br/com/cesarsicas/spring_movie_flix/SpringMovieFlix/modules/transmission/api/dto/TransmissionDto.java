package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.data.TransmissionEntity;

public record TransmissionDto(Long id, String movieName, String startTime, Integer duration, boolean isActive) {
    public TransmissionDto(TransmissionEntity e) {
        this(e.getId(), e.getMovieName(), e.getStartTime().toString(), e.getDuration(), e.isActive());
    }
}
