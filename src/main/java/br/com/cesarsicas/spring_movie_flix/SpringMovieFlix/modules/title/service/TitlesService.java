package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.service;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.data.ReviewRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.ReleaseEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.ReleasesRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.ReleaseMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.ReleaseResponseMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.WatchModeApiService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.TitleDetailsDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.TitleReleasesDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.TitleSearchDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Release;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TitlesService {

    @Autowired
    WatchModeApiService watchModeApi;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReleasesRepository releasesRepository;

    public List<Release> getReleases(Boolean useCache) {
        if (useCache){
            return releasesRepository.findAll().stream().map(ReleaseMapper::toDomain).toList();
        }

        var releases = watchModeApi.getReleases().releases().stream().map(ReleaseResponseMapper::toDomain)
                .toList();

        var entities = releases.stream().map(ReleaseEntity::new).toList();
        releasesRepository.saveAll(entities);
        return releases;
    }

    public TitleDetailsDto getTitleDetails(long id) {
        var details = watchModeApi.getTitleDetails(id);

        return new TitleDetailsDto(
                details);
    }

    public List<TitleSearchDto> searchTitles(String query) {
        return watchModeApi.getTitleSearch(query).results().stream().map(TitleSearchDto::new)
                .collect(Collectors.toList());
    }

}
