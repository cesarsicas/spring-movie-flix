package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.PersonRemote;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.ReleaseResponse;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.SearchResponse;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.TitleDetailsResponse;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.TitleListResponse;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.AutocompleteFilterResultType;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.AutocompleteSearchResponse;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.GenreRemote;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.SearchField;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WatchModeApiService {

    String baseUrl = "https://api.watchmode.com/v1/";

    @Value("${api.watchmode.key}")
    private String secret;

    @Autowired
    RestTemplate restTemplate;

    public ReleaseResponse getReleases(){
        return restTemplate.getForObject(
                baseUrl + "releases/?apiKey="+secret,
                ReleaseResponse.class);
    }

    public TitleDetailsResponse getTitleDetails(long id) {
        return restTemplate.getForObject(
                baseUrl + "title/" + id + "/details/?apiKey=" + secret + "&append_to_response=sources,cast-crew",
                TitleDetailsResponse.class);
    }

    public AutocompleteSearchResponse getAutocompleteSearch(String query, AutocompleteFilterResultType filterResultType) {
        return restTemplate.getForObject(
                baseUrl + "autocomplete-search/?apiKey=" + secret + "&search_type=" + filterResultType.value + "&search_value=" + query,
                AutocompleteSearchResponse.class);
    }

    public TitleListResponse listTitles(
            String types, String sourceIds, String genres, String regions,
            String sourceTypes, String networkIds, String languages,
            Integer releaseDateStart, Integer releaseDateEnd,
            Double userRatingLow, Double userRatingHigh,
            Integer criticScoreLow, Integer criticScoreHigh,
            Integer personId, String sortBy, Integer page, Integer limit) {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(baseUrl + "list-titles/")
                .queryParam("apiKey", secret);

        if (types != null)            builder.queryParam("types", types);
        if (sourceIds != null)        builder.queryParam("source_ids", sourceIds);
        if (genres != null)           builder.queryParam("genres", genres);
        if (regions != null)          builder.queryParam("regions", regions);
        if (sourceTypes != null)      builder.queryParam("source_types", sourceTypes);
        if (networkIds != null)       builder.queryParam("network_ids", networkIds);
        if (languages != null)        builder.queryParam("languages", languages);
        if (releaseDateStart != null) builder.queryParam("release_date_start", releaseDateStart);
        if (releaseDateEnd != null)   builder.queryParam("release_date_end", releaseDateEnd);
        if (userRatingLow != null)    builder.queryParam("user_rating_low", userRatingLow);
        if (userRatingHigh != null)   builder.queryParam("user_rating_high", userRatingHigh);
        if (criticScoreLow != null)   builder.queryParam("critic_score_low", criticScoreLow);
        if (criticScoreHigh != null)  builder.queryParam("critic_score_high", criticScoreHigh);
        if (personId != null)         builder.queryParam("person_id", personId);
        if (sortBy != null)           builder.queryParam("sort_by", sortBy);
        if (page != null)             builder.queryParam("page", page);
        if (limit != null)            builder.queryParam("limit", limit);

        return restTemplate.getForObject(builder.toUriString(), TitleListResponse.class);
    }

    public PersonRemote getPerson(long personId) {
        return restTemplate.getForObject(
                baseUrl + "person/" + personId + "/?apiKey=" + secret,
                PersonRemote.class);
    }

    public List<GenreRemote> getGenres() {
        GenreRemote[] response = restTemplate.getForObject(
                baseUrl + "genres/?apiKey=" + secret,
                GenreRemote[].class);
        return response != null ? Arrays.asList(response) : List.of();
    }

    public SearchResponse search(SearchField searchField, String searchValue, String types) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(baseUrl + "search/")
                .queryParam("apiKey", secret)
                .queryParam("search_field", searchField.name())
                .queryParam("search_value", searchValue);
        if (types != null) builder.queryParam("types", types);
        return restTemplate.getForObject(builder.toUriString().replace("%20", "+"), SearchResponse.class);
    }
}
