package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.data.remote.WatchModeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/titles")
public class TitlesController {

    @Autowired
    TitlesService titlesService;

    @Autowired
    WatchModeApiService watchModeService;


    @GetMapping("/releases")
    public ResponseEntity list(Pageable pageable) {
        var movie = watchModeService.getReleases();
        return ResponseEntity.ok(movie);
    }
}
