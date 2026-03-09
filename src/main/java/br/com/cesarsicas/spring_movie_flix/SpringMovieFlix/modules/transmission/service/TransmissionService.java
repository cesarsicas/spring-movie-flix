package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.service;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.infra.LiveStreamService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.data.TransmissionMovieRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.api.dto.CreateTransmissionDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.data.TransmissionEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.data.TransmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransmissionService {

    @Autowired
    private TransmissionRepository transmissionRepository;

    @Autowired
    private TransmissionMovieRepository movieRepository;

    @Autowired
    private LiveStreamService liveStreamService;

    public Optional<TransmissionEntity> getCurrentTransmission() {
        return transmissionRepository.findByIsActiveTrue();
    }

    public TransmissionEntity startTransmission(CreateTransmissionDto dto) {
        transmissionRepository.findByIsActiveTrue().ifPresent(t -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There is already an active transmission");
        });

        var movie = movieRepository.findById(dto.movieId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));

        var transmission = new TransmissionEntity(
                null,
                movie.getTitle(),
                LocalDateTime.now(),
                movie.getDuration(),
                true
        );

        var saved = transmissionRepository.save(transmission);

        try {
            liveStreamService.start();
        } catch (IOException e) {
            saved.setActive(false);
            transmissionRepository.save(saved);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to start live stream");
        }

        return saved;
    }

    public void stopTransmission() {
        var transmission = transmissionRepository.findByIsActiveTrue()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No active transmission"));
        transmission.setActive(false);
        transmissionRepository.save(transmission);
        liveStreamService.stop();
    }
}
