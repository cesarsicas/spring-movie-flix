package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.api.dto;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.data.ReviewEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.domain.Review;

public record GetReviewDto(
        Long id,
        Long externalTitleId,
        String review,
        Long defaultUserId,
        String defaultUserName
) {
    public GetReviewDto(ReviewEntity reviewEntity) {
        this(reviewEntity.getId(),
                reviewEntity.getExternalTitleId(),
                reviewEntity.getReview(),
                reviewEntity.getDefaultUser().getId(),
                reviewEntity.getDefaultUser().getName()
        );
    }

    public GetReviewDto(Review review) {
        this(review.getId(),
                review.getExternalTitleId(),
                review.getReview(),
                review.getDefaultUserId(),
                review.getDefaultUserName()
        );
    }
}
