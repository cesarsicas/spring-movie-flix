package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.movie;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.movie.data.remote.WatchModeApiService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.movie.dto.MovieReleasesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    WatchModeApiService watchModeApi;

    public List<MovieReleasesDto> getMovies() {
        return watchModeApi.getReleases().releases().stream().map(MovieReleasesDto::new)
                .collect(Collectors.toList());
    }

}
