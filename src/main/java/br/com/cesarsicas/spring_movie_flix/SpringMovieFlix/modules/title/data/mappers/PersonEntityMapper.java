package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.PersonEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Person;

public class PersonEntityMapper {

    public static Person toDomain(PersonEntity entity) {
        if (entity == null) return null;
        return new Person(
                entity.getId(),
                entity.getExternalId(),
                entity.getFull_name(),
                entity.getFirst_name(),
                entity.getLast_name(),
                entity.getTmdb_id(),
                entity.getImdb_id(),
                entity.getMain_profession(),
                entity.getSecondary_profession(),
                entity.getTertiary_profession(),
                entity.getDate_of_birth(),
                entity.getDate_of_death(),
                entity.getPlace_of_birth(),
                entity.getGender(),
                entity.getHeadshot_url(),
                entity.getKnown_for(),
                entity.getRelevance_percentile()
        );
    }

    public static PersonEntity toEntity(Person domain) {
        if (domain == null) return null;
        var entity = new PersonEntity();
        entity.setExternalId(domain.externalId());
        entity.setFull_name(domain.full_name());
        entity.setFirst_name(domain.first_name());
        entity.setLast_name(domain.last_name());
        entity.setTmdb_id(domain.tmdb_id());
        entity.setImdb_id(domain.imdb_id());
        entity.setMain_profession(domain.main_profession());
        entity.setSecondary_profession(domain.secondary_profession());
        entity.setTertiary_profession(domain.tertiary_profession());
        entity.setDate_of_birth(domain.date_of_birth());
        entity.setDate_of_death(domain.date_of_death());
        entity.setPlace_of_birth(domain.place_of_birth());
        entity.setGender(domain.gender());
        entity.setHeadshot_url(domain.headshot_url());
        entity.setKnown_for(domain.known_for());
        entity.setRelevance_percentile(domain.relevance_percentile());
        return entity;
    }
}
