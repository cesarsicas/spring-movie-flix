package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TitleDetailsRepository extends JpaRepository<TitleDetailsEntity, Long> {

    Optional<TitleDetailsEntity> findByExternalId(Long externalId);
}
