package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.data.remote.WatchModeApiService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.dto.TitleReleasesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TitlesService {

    @Autowired
    WatchModeApiService watchModeApi;

    public List<TitleReleasesDto> getMovies() {
        return watchModeApi.getReleases().releases().stream().map(TitleReleasesDto::new)
                .collect(Collectors.toList());
    }

}
