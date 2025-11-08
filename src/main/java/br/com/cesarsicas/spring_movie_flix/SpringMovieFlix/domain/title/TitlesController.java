package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.dto.TitleDetailsDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.dto.TitleReleasesDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.dto.TitleSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/titles")
public class TitlesController {

    @Autowired
    TitlesService titlesService;

    @GetMapping("/releases")
    public ResponseEntity<List<TitleReleasesDto>> releases() {
        var movie = titlesService.getReleases();
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/{id}")
    public ResponseEntity details(@PathVariable long id) {
        var details = titlesService.getTitleDetails(id);
        return ResponseEntity.ok(details);
    }


    @GetMapping("/search")
    public ResponseEntity<List<TitleSearchDto>>  search(@RequestParam String query) {
        var movie = titlesService.searchTitles(query);
        return ResponseEntity.ok(movie);
    }
}
