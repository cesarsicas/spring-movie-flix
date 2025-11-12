package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.review.data;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.default_user.data.DefaultUserEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.review.dto.SaveReviewDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.user.data.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Table(name = "reviews")
@Entity(name = "Review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String review;

    private LocalDateTime datetime;

    private Long externalTitleId;

    @ManyToOne
    @JoinColumn(name = "default_user_id", referencedColumnName = "id")
    private DefaultUserEntity defaultUser;

    public ReviewEntity(DefaultUserEntity user, SaveReviewDto saveReviewDto) {
        this.review = saveReviewDto.review();
        this.externalTitleId = saveReviewDto.externalTitleId();
        this.defaultUser = user;
        this.datetime = LocalDateTime.now(ZoneId.of("GMT-03:00"));
    }
}