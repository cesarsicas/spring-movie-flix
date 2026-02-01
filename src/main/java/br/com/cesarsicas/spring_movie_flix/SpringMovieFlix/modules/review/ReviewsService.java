package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.data.DefaultUserRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.shared.exceptions.DefaultUserNotFoundException;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.data.ReviewEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.data.ReviewRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.api.dto.GetReviewDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.review.api.dto.SaveReviewDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.data.UserEntity;
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
