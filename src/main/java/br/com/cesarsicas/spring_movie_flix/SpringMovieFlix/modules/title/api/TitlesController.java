package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.api.dto.GetReviewDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.service.ReviewsService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.PersonDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.SearchResultDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.TitleDetailsDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.TitleListResponseDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.TitleReleasesDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.service.TitlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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


    @GetMapping("/list")
    public ResponseEntity<TitleListResponseDto> listTitles(
            @RequestParam(required = false) String types,
            @RequestParam(required = false) String source_ids,
            @RequestParam(required = false) String genres,
            @RequestParam(required = false) String regions,
            @RequestParam(required = false) String source_types,
            @RequestParam(required = false) String network_ids,
            @RequestParam(required = false) String languages,
            @RequestParam(required = false) Integer release_date_start,
            @RequestParam(required = false) Integer release_date_end,
            @RequestParam(required = false) Double user_rating_low,
            @RequestParam(required = false) Double user_rating_high,
            @RequestParam(required = false) Integer critic_score_low,
            @RequestParam(required = false) Integer critic_score_high,
            @RequestParam(required = false) Integer person_id,
            @RequestParam(required = false, defaultValue = "relevance_desc") String sort_by,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false, defaultValue = "false") Boolean useCache) {
        var result = titlesService.listTitles(types, source_ids, genres, regions, source_types,
                network_ids, languages, release_date_start, release_date_end,
                user_rating_low, user_rating_high, critic_score_low, critic_score_high,
                person_id, sort_by, page, limit, useCache);
        return ResponseEntity.ok(new TitleListResponseDto(result));
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<PersonDto> getPerson(
            @PathVariable long personId,
            @RequestParam(required = false, defaultValue = "false") Boolean useCache) {
        var person = titlesService.getPerson(personId, useCache);
        return ResponseEntity.ok(new PersonDto(person));
    }

    @GetMapping("/search")
    public ResponseEntity<SearchResultDto> search(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "name") String searchField,
            @RequestParam(required = false) String types) {
        var result = titlesService.searchTitles(query, searchField, types);
        return ResponseEntity.ok(new SearchResultDto(result));
    }

    //todo: externalId is not being used yet
    //the video returned is not dynamic
    @GetMapping("/{externalId}/stream")
    public ResponseEntity<byte[]> getVideoById(
            @PathVariable Long externalId,
            @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {

        long start = 0;
        long end = -1;

        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String[] ranges = rangeHeader.substring(6).split("-");
            start = Long.parseLong(ranges[0]);
            if (ranges.length > 1 && !ranges[1].isEmpty()) {
                end = Long.parseLong(ranges[1]);
            }
        }

        var chunk = titlesService.getVideoChunk(start, end);
        if (chunk.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var video = chunk.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("video/mp4"));
        headers.add("Content-Range", "bytes " + video.start() + "-" + video.end() + "/" + video.fileSize());
        headers.add("Accept-Ranges", "bytes");
        headers.setContentLength(video.data().length);

        HttpStatus status = (rangeHeader != null) ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK;
        return ResponseEntity.status(status).headers(headers).body(video.data());
    }
}
