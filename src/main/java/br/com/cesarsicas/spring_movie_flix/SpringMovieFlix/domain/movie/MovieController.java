package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.movie;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.movie.data.remote.WatchModeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieService movieService;

    @Autowired
    WatchModeApiService watchModeService;


    @GetMapping("/releases")
    public ResponseEntity list(Pageable pageable) {
        var movie = watchModeService.getReleases();
        return ResponseEntity.ok(movie);
    }
}
