package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain;

public record TitleSource(
        int source_id,
        String name,
        String type,
        String region,
        String ios_url,
        String android_url,
        String web_url,
        String format,
        Double price,
        Integer seasons,
        Integer episodes
) {}
