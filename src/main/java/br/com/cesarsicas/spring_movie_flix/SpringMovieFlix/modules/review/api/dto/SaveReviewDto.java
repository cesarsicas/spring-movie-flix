package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.api.dto;

public record SaveReviewDto(
        Long externalTitleId,
        String review
) {
}
