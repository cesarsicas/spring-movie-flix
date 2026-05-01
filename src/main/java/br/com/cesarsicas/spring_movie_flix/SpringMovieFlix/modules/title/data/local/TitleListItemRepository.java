package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TitleListItemRepository extends JpaRepository<TitleListItemEntity, Long> {

    Optional<TitleListItemEntity> findByExternalId(Long externalId);

    @Query("SELECT t.externalId FROM TitleListItem t WHERE t.externalId IN :externalIds")
    List<Long> findExternalIdsByExternalIdIn(@Param("externalIds") List<Long> externalIds);
}
