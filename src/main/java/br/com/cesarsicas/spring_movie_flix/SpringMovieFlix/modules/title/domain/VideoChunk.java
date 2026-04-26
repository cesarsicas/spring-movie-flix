package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain;

public record VideoChunk(byte[] data, long start, long end, long fileSize) {}
