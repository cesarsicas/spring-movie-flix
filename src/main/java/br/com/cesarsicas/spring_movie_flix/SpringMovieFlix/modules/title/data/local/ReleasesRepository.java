package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReleasesRepository extends JpaRepository<ReleaseEntity, Long> {

    /**
     * Returns the external_ids that already exist in the database.
     * Used to avoid inserting duplicate releases when syncing from API.
     */
    List<Long> findExternal_idByExternal_idIn(List<Long> externalIds);

    Optional<ReleaseEntity> findByExternal_id(long externalId);

    List<ReleaseEntity> findByTitleContainingIgnoreCase(String title);
}
