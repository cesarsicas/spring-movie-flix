package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local;

import org.springframework.data.jpa.repository.JpaRepository;
public interface ReleasesRepository extends JpaRepository<ReleaseEntity, Long> {

}
