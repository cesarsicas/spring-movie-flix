package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

    @Query("SELECT g.externalId FROM Genre g WHERE g.externalId IN :externalIds")
    List<Integer> findExternalIdsByExternalIdIn(@Param("externalIds") List<Integer> externalIds);
}
