package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.SearchResponse;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.PersonSearchItem;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.SearchResult;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleSearchItem;

import java.util.List;

public class SearchResultMapper {

    public static SearchResult toDomain(SearchResponse response) {
        if (response == null) return new SearchResult(List.of(), List.of());

        List<TitleSearchItem> titles = response.title_results() == null
                ? List.of()
                : response.title_results().stream()
                        .map(r -> new TitleSearchItem(r.id(), r.name(), r.type(),
                                r.year(), r.imdb_id(), r.tmdb_id(), r.tmdb_type()))
                        .toList();

        List<PersonSearchItem> people = response.people_results() == null
                ? List.of()
                : response.people_results().stream()
                        .map(r -> new PersonSearchItem(r.id(), r.name(), r.main_profession(),
                                r.imdb_id(), r.tmdb_id()))
                        .toList();

        return new SearchResult(titles, people);
    }
}
