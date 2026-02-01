package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.api;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.ReviewsService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.api.dto.GetReviewDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.api.dto.SaveReviewDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.data.UserEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
