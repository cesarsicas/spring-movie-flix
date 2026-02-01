package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.data.DefaultUserEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.data.DefaultUserRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.api.dto.CreateUpdateDefaultUserDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.api.dto.GetDefaultUserDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.shared.exceptions.DefaultUserNotFoundException;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.data.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

;

@Service
public class DefaultUserService {

    @Autowired
    DefaultUserRepository repository;

    public GetDefaultUserDto getDefaultUser(UserEntity user) throws DefaultUserNotFoundException {
        var defaultUser = repository.findByUser(user);

        if (defaultUser.isPresent()) {
            return new GetDefaultUserDto(defaultUser.get());
        } else {
            throw new DefaultUserNotFoundException();
        }
    }

    @Transactional
    public GetDefaultUserDto createOrUpdateDefaultUser(UserEntity user, CreateUpdateDefaultUserDto data) {
        var userEntityOptional = repository.findByUser(user);

        DefaultUserEntity entity;

        if (userEntityOptional.isPresent()) {
            entity = userEntityOptional.get();
            entity.setName(data.name());
            entity.setBio(data.bio());
        } else {
            entity = new DefaultUserEntity(data);
        }
        return new GetDefaultUserDto(repository.save(entity));
    }
}
