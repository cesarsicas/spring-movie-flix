package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.api.dto.GetReviewDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.service.ReviewsService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.AutocompleteSearchResponseDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.GenreDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.PersonDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.SearchTitlesAndPeopleResultDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.AutocompleteFilterResultType;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.SearchField;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.TitleDetailsDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.TitleListResponseDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto.TitleReleasesDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.service.TitlesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "Titles", description = "Movie and TV title catalog endpoints")
@RestController
@RequestMapping("/titles")
public class TitlesController {

    @Autowired
    TitlesService titlesService;

    @Autowired
    ReviewsService reviewsService;


    @Operation(summary = "List genres", description = "Returns the full genre list, optionally served from cache.")
    @ApiResponse(responseCode = "200", description = "Genre list returned successfully")
    @GetMapping("/genres")
    public ResponseEntity<List<GenreDto>> getGenres(
            @Parameter(description = "Return cached data when available") @RequestParam(required = false, defaultValue = "false") Boolean useCache) {
        var genres = titlesService.getGenres(useCache).stream()
                .map(GenreDto::new)
                .toList();
        return ResponseEntity.ok(genres);
    }

    @Operation(summary = "Get recent releases", description = "Returns a list of recently released titles.")
    @ApiResponse(responseCode = "200", description = "Release list returned successfully")
    @GetMapping("/releases")
    public ResponseEntity<List<TitleReleasesDto>> releases(
            @Parameter(description = "Return cached data when available") @RequestParam(required = false, defaultValue = "false") Boolean useCache) {
        var movie = titlesService.getReleases(useCache).stream()
                .map(TitleReleasesDto::new)
                .toList();
        return ResponseEntity.ok(movie);
    }

    @Operation(summary = "Get title details", description = "Returns full details for a title by its WatchMode external ID, including streaming sources and cast/crew.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Title details returned successfully"),
            @ApiResponse(responseCode = "404", description = "Title not found")
    })
    @GetMapping("/{externalId}")
    public ResponseEntity<TitleDetailsDto> details(
            @Parameter(description = "WatchMode external title ID") @PathVariable long externalId,
            @Parameter(description = "Return cached data when available") @RequestParam(required = false, defaultValue = "false") Boolean useCache) {
        var details = titlesService.getTitleDetails(externalId, useCache);
        return ResponseEntity.ok(new TitleDetailsDto(details));
    }

    @Operation(summary = "Get title reviews", description = "Returns all user reviews for the given title.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reviews returned successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{externalId}/reviews")
    public ResponseEntity<List<GetReviewDto>> getReviews(
            @Parameter(description = "WatchMode external title ID") @PathVariable Long externalId) {
        try {
            var reviews = reviewsService.getReviewsByExternalTitleId(externalId).stream()
                    .map(GetReviewDto::new)
                    .toList();
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @Operation(summary = "List titles with filters", description = "Returns a paginated list of titles filtered by type, genre, source, rating, and more.")
    @ApiResponse(responseCode = "200", description = "Title list returned successfully")
    @GetMapping("/list")
    public ResponseEntity<TitleListResponseDto> listTitles(
            @Parameter(description = "Comma-separated title types (e.g. movie,tv_movie)") @RequestParam(required = false) String types,
            @Parameter(description = "Comma-separated source IDs") @RequestParam(required = false) String source_ids,
            @Parameter(description = "Comma-separated genre IDs") @RequestParam(required = false) String genres,
            @Parameter(description = "Comma-separated region codes") @RequestParam(required = false) String regions,
            @Parameter(description = "Comma-separated source types") @RequestParam(required = false) String source_types,
            @Parameter(description = "Comma-separated network IDs") @RequestParam(required = false) String network_ids,
            @Parameter(description = "Comma-separated language codes") @RequestParam(required = false) String languages,
            @Parameter(description = "Release date start (Unix timestamp)") @RequestParam(required = false) Integer release_date_start,
            @Parameter(description = "Release date end (Unix timestamp)") @RequestParam(required = false) Integer release_date_end,
            @Parameter(description = "Minimum user rating") @RequestParam(required = false) Double user_rating_low,
            @Parameter(description = "Maximum user rating") @RequestParam(required = false) Double user_rating_high,
            @Parameter(description = "Minimum critic score") @RequestParam(required = false) Integer critic_score_low,
            @Parameter(description = "Maximum critic score") @RequestParam(required = false) Integer critic_score_high,
            @Parameter(description = "Filter by person ID") @RequestParam(required = false) Integer person_id,
            @Parameter(description = "Sort order (default: relevance_desc)") @RequestParam(required = false, defaultValue = "relevance_desc") String sort_by,
            @Parameter(description = "Page number (default: 1)") @RequestParam(required = false, defaultValue = "1") Integer page,
            @Parameter(description = "Results per page (default: 10)") @RequestParam(required = false, defaultValue = "10") Integer limit,
            @Parameter(description = "Return cached data when available") @RequestParam(required = false, defaultValue = "false") Boolean useCache) {
        var result = titlesService.listTitles(types, source_ids, genres, regions, source_types,
                network_ids, languages, release_date_start, release_date_end,
                user_rating_low, user_rating_high, critic_score_low, critic_score_high,
                person_id, sort_by, page, limit, useCache);
        return ResponseEntity.ok(new TitleListResponseDto(result));
    }

    @Operation(summary = "Get person details", description = "Returns details for a cast or crew member by their WatchMode person ID.")
    @ApiResponse(responseCode = "200", description = "Person details returned successfully")
    @GetMapping("/person/{personId}")
    public ResponseEntity<PersonDto> getPerson(
            @Parameter(description = "WatchMode person ID") @PathVariable long personId,
            @Parameter(description = "Return cached data when available") @RequestParam(required = false, defaultValue = "false") Boolean useCache) {
        var person = titlesService.getPerson(personId, useCache);
        return ResponseEntity.ok(new PersonDto(person));
    }

    @Operation(summary = "Autocomplete search", description = "Returns autocomplete suggestions for titles and/or people based on a partial query.")
    @ApiResponse(responseCode = "200", description = "Autocomplete results returned")
    @GetMapping("/autocomplete-search")
    public ResponseEntity<AutocompleteSearchResponseDto> autocompleteSearch(
            @Parameter(description = "Partial search query", required = true) @RequestParam String query,
            @Parameter(description = "Filter by result type (TITLES_AND_PEOPLE, TITLES_ONLY, MOVIES_ONLY, TV_SHOWS_ONLY, PEOPLE_ONLY)") @RequestParam(required = false, defaultValue = "TITLES_AND_PEOPLE") AutocompleteFilterResultType filterResultType) {
        var result = titlesService.getAutocompleteSearch(query, filterResultType);
        return ResponseEntity.ok(new AutocompleteSearchResponseDto(result));
    }

    @Operation(summary = "Search titles and people", description = "Search by a specific field such as name, IMDb ID, or TMDb ID.")
    @ApiResponse(responseCode = "200", description = "Search results returned")
    @GetMapping("/search")
    public ResponseEntity<SearchTitlesAndPeopleResultDto> searchTitlesAndPeople(
            @Parameter(description = "Value to search for", required = true) @RequestParam String searchValue,
            @Parameter(description = "Field to search by: name, imdb_id, tmdb_movie_id, tmdb_tv_id, tmdb_person_id", required = true) @RequestParam SearchField searchField,
            @Parameter(description = "Filter by title types (e.g. movie,tv_movie)") @RequestParam(required = false) String types) {
        var result = titlesService.searchTitlesAndPeople(searchValue, searchField, types);
        return ResponseEntity.ok(new SearchTitlesAndPeopleResultDto(result));
    }

    @Operation(summary = "Stream title video", description = "Returns a byte-range video stream for the given title. Supports HTTP 206 partial content.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Full video content"),
            @ApiResponse(responseCode = "206", description = "Partial video content (range request)"),
            @ApiResponse(responseCode = "404", description = "Video not found")
    })

    //todo: externalId is not being used yet
    //the video returned is not dynamic

    @GetMapping("/{externalId}/stream")
    public ResponseEntity<byte[]> getVideoById(
            @Parameter(description = "WatchMode external title ID") @PathVariable Long externalId,
            @Parameter(description = "Byte range (e.g. bytes=0-1023)") @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {

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
