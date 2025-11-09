package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.default_user;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.default_user.data.DefaultUserRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.default_user.dto.DefaultUserDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.exceptions.DefaultUserNotFoundException;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.user.data.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService {

    @Autowired
    DefaultUserRepository repository;

    public DefaultUserDto getDefaultUser(UserEntity user) throws DefaultUserNotFoundException {
        var defaultUser = repository.findByUser(user);

        if (defaultUser.isPresent()){
            return new DefaultUserDto(defaultUser.get());
        }
        else{
            throw new DefaultUserNotFoundException();
        }
    }
}
