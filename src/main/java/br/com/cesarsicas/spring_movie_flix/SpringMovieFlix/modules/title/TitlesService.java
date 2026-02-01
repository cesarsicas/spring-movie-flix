package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.data.ReviewRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.ReleaseEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.ReleasesRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.WatchModeApiService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.dto.TitleDetailsDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.dto.TitleReleasesDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.dto.TitleSearchDto;
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

    public List<TitleReleasesDto> getReleases(Boolean useCache) {
        if (useCache){
            return releasesRepository.findAll().stream().map(TitleReleasesDto::new)
                    .toList();
        }

        var dtos = watchModeApi.getReleases().releases().stream().map(TitleReleasesDto::new)
                .toList();

        var entities = dtos.stream().map(ReleaseEntity::new).toList();


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
