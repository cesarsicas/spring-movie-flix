package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TitleReleaseRepository extends JpaRepository<TitleReleaseEntity, Long> {

    /**
     * Returns the external_ids that already exist in the database.
     * Used to avoid inserting duplicate releases when syncing from API.
     */
    @Query("SELECT tr.external_id FROM TitleRelease tr WHERE tr.external_id IN :externalIds")
    List<Long> findExternal_idByExternal_idIn(@Param("externalIds") List<Long> externalIds);

    @Query("SELECT tr FROM TitleRelease tr WHERE tr.external_id = :externalId")
    Optional<TitleReleaseEntity> findByExternal_id(@Param("externalId") long externalId);

    List<TitleReleaseEntity> findByTitleContainingIgnoreCase(String title);
}
