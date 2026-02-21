package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.data;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.domain.Review;

public class ReviewMapper {

    public static Review toDomain(ReviewEntity entity) {
        if (entity == null) return null;
        var defaultUserId = entity.getDefaultUser() != null ? entity.getDefaultUser().getId() : null;
        var defaultUserName = entity.getDefaultUser() != null ? entity.getDefaultUser().getName() : null;
        return new Review(
                entity.getId(),
                entity.getReview(),
                entity.getDatetime(),
                entity.getExternalTitleId(),
                defaultUserId,
                defaultUserName
        );
    }
}
