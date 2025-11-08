package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.data.local;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TitlesRepository extends JpaRepository<TitleEntity, Long> {

}
