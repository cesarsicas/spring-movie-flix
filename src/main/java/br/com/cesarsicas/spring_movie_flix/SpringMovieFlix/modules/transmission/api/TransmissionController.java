package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.api;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.api.dto.TransmissionMovieDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.data.TransmissionMovieRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.api.dto.CreateTransmissionDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.api.dto.TransmissionDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.service.TransmissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transmissions")
public class TransmissionController {

    @Autowired
    private TransmissionService service;

    @Autowired
    private TransmissionMovieRepository movieRepository;

    @GetMapping("/movies")
    public ResponseEntity<List<TransmissionMovieDto>> getMovies() {
        var movies = movieRepository.findAll().stream().map(TransmissionMovieDto::new).toList();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/current")
    public ResponseEntity<TransmissionDto> getCurrent() {
        return service.getCurrentTransmission()
                .map(t -> ResponseEntity.ok(new TransmissionDto(t)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TransmissionDto> startTransmission(@RequestBody @Valid CreateTransmissionDto dto) {
        var transmission = service.startTransmission(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TransmissionDto(transmission));
    }
}
