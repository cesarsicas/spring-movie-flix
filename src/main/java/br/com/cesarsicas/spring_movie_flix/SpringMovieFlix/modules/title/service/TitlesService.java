package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.service;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.ReleaseEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.ReleasesRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.ReleaseMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.ReleaseResponseMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.ReleaseToTitleDetailsMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.ReleaseToTitleSearchMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.TitleDetailsResponseMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.TitleSearchMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.WatchModeApiService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Release;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleDetails;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TitlesService {

    @Autowired
    WatchModeApiService watchModeApi;

    @Autowired
    ReleasesRepository releasesRepository;

    public List<Release> getReleases(Boolean useCache) {
        if (useCache){
            return releasesRepository.findAll().stream().map(ReleaseMapper::toDomain).toList();
        }

        var releases = watchModeApi.getReleases().releaseRemotes().stream().map(ReleaseResponseMapper::toDomain)
                .toList();

        var externalIds = releases.stream().mapToLong(Release::getExternal_id).boxed().toList();
        var existingExternalIds = Set.copyOf(
                releasesRepository.findExternal_idByExternal_idIn(externalIds));

        var newReleases = releases.stream()
                .filter(r -> !existingExternalIds.contains(r.getExternal_id()))
                .toList();
        var entities = newReleases.stream().map(ReleaseEntity::new).toList();
        releasesRepository.saveAll(entities);

        return releases;
    }

    public TitleDetails getTitleDetails(long externalId, Boolean useCache) {
        if (Boolean.TRUE.equals(useCache)) {
            Optional<TitleDetails> cached = releasesRepository.findByExternal_id(externalId)
                    .map(ReleaseMapper::toDomain)
                    .map(ReleaseToTitleDetailsMapper::toTitleDetails);
            if (cached.isPresent()) return cached.get();
        }
        var response = watchModeApi.getTitleDetails(externalId);
        return TitleDetailsResponseMapper.toDomain(response);
    }

    public List<TitleSearch> searchTitles(String query, Boolean useCache) {
        if (Boolean.TRUE.equals(useCache)) {
            return releasesRepository.findByTitleContainingIgnoreCase(query).stream()
                    .map(ReleaseMapper::toDomain)
                    .map(ReleaseToTitleSearchMapper::toTitleSearch)
                    .toList();
        }
        return watchModeApi.getTitleSearch(query).results().stream()
                .map(TitleSearchMapper::toDomain)
                .toList();
    }

}
