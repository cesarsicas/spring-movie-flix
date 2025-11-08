package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.data.remote;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.data.remote.model.ReleaseResponse;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.data.remote.model.TitleDetailsResponse;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.data.remote.model.TitleSearch;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.data.remote.model.TitleSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
                baseUrl + "title/" +id+"/details/?apiKey="+secret,
                TitleDetailsResponse.class);
    }

    public TitleSearchResponse getTitleSearch(String query) {
        return restTemplate.getForObject(
                baseUrl + "autocomplete-search/?apiKey="+secret + "&search_type=2&search_value="+query,
                TitleSearchResponse.class);
    }
}
