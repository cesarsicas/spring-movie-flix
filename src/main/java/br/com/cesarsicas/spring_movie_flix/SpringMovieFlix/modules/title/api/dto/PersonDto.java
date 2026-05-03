package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Person;

import java.util.List;

public record PersonDto(
        Long id,
        Long externalId,
        String full_name,
        String first_name,
        String last_name,
        Integer tmdb_id,
        String imdb_id,
        String main_profession,
        String secondary_profession,
        String tertiary_profession,
        String date_of_birth,
        String date_of_death,
        String place_of_birth,
        String gender,
        String headshot_url,
        List<Integer> known_for,
        Double relevance_percentile
) {
    public PersonDto(Person person) {
        this(person.id(), person.externalId(), person.full_name(), person.first_name(),
                person.last_name(), person.tmdb_id(), person.imdb_id(), person.main_profession(),
                person.secondary_profession(), person.tertiary_profession(), person.date_of_birth(),
                person.date_of_death(), person.place_of_birth(), person.gender(),
                person.headshot_url(), person.known_for(), person.relevance_percentile());
    }
}
