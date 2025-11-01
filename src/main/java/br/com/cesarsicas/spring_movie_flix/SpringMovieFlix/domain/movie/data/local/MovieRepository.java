package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.movie.data.local;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

}
