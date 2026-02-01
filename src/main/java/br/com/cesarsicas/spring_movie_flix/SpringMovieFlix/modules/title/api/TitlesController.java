package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.service.ReviewsService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.api.dto.GetReviewDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.service.TitlesService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.TitleReleasesDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.TitleSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/titles")
public class TitlesController {

    @Autowired
    TitlesService titlesService;

    @Autowired
    ReviewsService reviewsService;


    @GetMapping("/releases")
    public ResponseEntity<List<TitleReleasesDto>> releases(@RequestParam Boolean useCache) {
        var movie = titlesService.getReleases(useCache);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/{id}")
    public ResponseEntity details(@PathVariable long id) {
        var details = titlesService.getTitleDetails(id);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<GetReviewDto>> getReviews(@PathVariable Long id) {
        try {
            var reviews = reviewsService.getReviewsByExternalTitleId(id);
            return ResponseEntity.ok(reviews);
        }
        catch (Exception e){
            return  ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/search")
    public ResponseEntity<List<TitleSearchDto>>  search(@RequestParam String query) {
        var movie = titlesService.searchTitles(query);
        return ResponseEntity.ok(movie);
    }
}
