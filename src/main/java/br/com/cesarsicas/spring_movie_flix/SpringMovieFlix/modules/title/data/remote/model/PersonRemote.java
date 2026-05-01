package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model;

import java.util.List;

public record PersonRemote(
        Long id,
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
) {}
