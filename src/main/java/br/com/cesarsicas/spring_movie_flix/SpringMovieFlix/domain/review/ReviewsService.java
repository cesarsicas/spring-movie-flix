package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.review;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.default_user.data.DefaultUserRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.exceptions.DefaultUserNotFoundException;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.review.data.ReviewEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.review.data.ReviewRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.review.dto.GetReviewDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.review.dto.SaveReviewDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.user.data.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewsService {

    @Autowired
    ReviewRepository repository;

    @Autowired
    DefaultUserRepository userRepository;

    @Transactional
    public GetReviewDto saveReview(UserEntity user, SaveReviewDto saveReviewDto) throws DefaultUserNotFoundException {

        var defaultUser = userRepository.findByUser(user);

        if (defaultUser.isPresent()) {
            var reviewEntity = repository.save(new ReviewEntity(defaultUser.get(), saveReviewDto));

            return  new GetReviewDto(reviewEntity);
        } else {
            throw new DefaultUserNotFoundException();
        }
    }

    public List<GetReviewDto> getReviewsByExternalTitleId(Long externalTitleId) {
        return repository.findAllByExternalTitleId(externalTitleId).stream().map(GetReviewDto::new).toList();
    }


}
