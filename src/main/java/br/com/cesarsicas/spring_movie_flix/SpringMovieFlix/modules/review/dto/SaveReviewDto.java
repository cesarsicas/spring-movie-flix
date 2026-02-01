package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.dto;

public record SaveReviewDto(
        Long externalTitleId,
        String review
) {
}
