package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.movie.data.remote;

import java.util.List;

public record ReleaseResponse(
        List<Release> releases
) {



}