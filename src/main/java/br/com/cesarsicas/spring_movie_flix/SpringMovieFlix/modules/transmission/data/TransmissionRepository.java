package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransmissionRepository extends JpaRepository<TransmissionEntity, Long> {
    Optional<TransmissionEntity> findByIsActiveTrue();
}
