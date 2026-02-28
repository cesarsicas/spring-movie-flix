package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.service.ReviewsService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.api.dto.GetReviewDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.service.TitlesService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.TitleDetailsDto;
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
    public ResponseEntity<List<TitleReleasesDto>> releases(
            @RequestParam(required = false, defaultValue = "false") Boolean useCache) {
        var movie = titlesService.getReleases(useCache).stream()
                .map(TitleReleasesDto::new)
                .toList();
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<TitleDetailsDto> details(
            @PathVariable long externalId,
            @RequestParam(required = false, defaultValue = "false") Boolean useCache) {
        var details = titlesService.getTitleDetails(externalId, useCache);
        return ResponseEntity.ok(new TitleDetailsDto(details));
    }

    @GetMapping("/{externalId}/reviews")
    public ResponseEntity<List<GetReviewDto>> getReviews(@PathVariable Long externalId) {
        try {
            var reviews = reviewsService.getReviewsByExternalTitleId(externalId).stream()
                    .map(GetReviewDto::new)
                    .toList();
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/search")
    public ResponseEntity<List<TitleSearchDto>> search(
            @RequestParam String query) {
        var results = titlesService.searchTitles(query).stream()
                .map(TitleSearchDto::new)
                .toList();
        return ResponseEntity.ok(results);
    }
}
