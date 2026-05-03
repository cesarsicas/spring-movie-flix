package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model;

public record CastCrewMemberRemote(
        long person_id,
        String type,
        String full_name,
        String headshot_url,
        String role,
        int episode_count,
        int order
) {}
