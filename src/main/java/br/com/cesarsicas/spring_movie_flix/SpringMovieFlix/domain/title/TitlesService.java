package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.data.remote.WatchModeApiService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.dto.TitleDetailsDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.dto.TitleReleasesDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.dto.TitleSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TitlesService {

    @Autowired
    WatchModeApiService watchModeApi;

    public List<TitleReleasesDto> getReleases() {
        return watchModeApi.getReleases().releases().stream().map(TitleReleasesDto::new)
                .collect(Collectors.toList());
    }

    public TitleDetailsDto getTitleDetails(long id) {
        var details = watchModeApi.getTitleDetails(id);
        return new TitleDetailsDto(details);
    }

    public List<TitleSearchDto> searchTitles(String query) {
        return watchModeApi.getTitleSearch(query).results().stream().map(TitleSearchDto::new)
                .collect(Collectors.toList());
    }


}
