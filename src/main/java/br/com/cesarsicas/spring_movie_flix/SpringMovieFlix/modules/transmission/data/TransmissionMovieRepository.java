package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransmissionMovieRepository extends JpaRepository<TransmissionMovieEntity, Long> {
}
