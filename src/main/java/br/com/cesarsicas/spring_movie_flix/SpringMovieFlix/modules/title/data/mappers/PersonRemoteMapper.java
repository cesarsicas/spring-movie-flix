package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.PersonRemote;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Person;

public class PersonRemoteMapper {

    public static Person toDomain(PersonRemote remote) {
        if (remote == null) return null;
        return new Person(
                null,
                remote.id(),
                remote.full_name(),
                remote.first_name(),
                remote.last_name(),
                remote.tmdb_id(),
                remote.imdb_id(),
                remote.main_profession(),
                remote.secondary_profession(),
                remote.tertiary_profession(),
                remote.date_of_birth(),
                remote.date_of_death(),
                remote.place_of_birth(),
                remote.gender(),
                remote.headshot_url(),
                remote.known_for(),
                remote.relevance_percentile()
        );
    }
}
