package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.service.ReviewsService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.api.dto.GetReviewDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.service.TitlesService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.TitleDetailsDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.TitleReleasesDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.TitleSearchDto;
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


    @GetMapping("/search")
    public ResponseEntity<List<TitleSearchDto>> search(
            @RequestParam String query) {
        var results = titlesService.searchTitles(query).stream()
                .map(TitleSearchDto::new)
                .toList();
        return ResponseEntity.ok(results);
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
