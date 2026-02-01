package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllByExternalTitleId(Long externalTitleId);

}
