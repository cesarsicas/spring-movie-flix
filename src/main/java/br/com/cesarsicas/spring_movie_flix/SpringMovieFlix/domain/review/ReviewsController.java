package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.review;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.default_user.dto.CreateUpdateDefaultUserDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.review.dto.GetReviewDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.review.dto.SaveReviewDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.user.data.UserEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {

    @Autowired
    ReviewsService reviewsService;

    @PostMapping
    public ResponseEntity<GetReviewDto> review(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody @Valid SaveReviewDto saveReviewDto
            ) {

        try{
            var review = reviewsService.saveReview(user, saveReviewDto);
            return ResponseEntity.ok(review);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
