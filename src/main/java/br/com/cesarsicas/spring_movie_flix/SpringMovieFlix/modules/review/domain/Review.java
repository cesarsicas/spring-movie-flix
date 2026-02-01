package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Review {
    private Long id;
    private String review;
    private LocalDateTime datetime;
    private Long externalTitleId;
    private Long defaultUserId;

    public Review() {}

    public Review(Long id, String review, LocalDateTime datetime, Long externalTitleId, Long defaultUserId) {
        this.id = id;
        this.review = review;
        this.datetime = datetime;
        this.externalTitleId = externalTitleId;
        this.defaultUserId = defaultUserId;
    }

}
