package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain;

public record CastCrewMember(
        long person_id,
        String type,
        String full_name,
        String headshot_url,
        String role,
        int episode_count,
        int order
) {}
