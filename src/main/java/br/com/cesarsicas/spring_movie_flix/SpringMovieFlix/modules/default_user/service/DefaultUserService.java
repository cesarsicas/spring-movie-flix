package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.service;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.data.DefaultUserEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.data.DefaultUserMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.data.DefaultUserRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.api.dto.CreateUpdateDefaultUserDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.domain.DefaultUser;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.shared.exceptions.DefaultUserNotFoundException;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.data.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService {

    @Autowired
    DefaultUserRepository repository;

    public DefaultUser getDefaultUser(UserEntity user) throws DefaultUserNotFoundException {
        var defaultUserEntity = repository.findByUser(user);

        if (defaultUserEntity.isPresent()) {
            return DefaultUserMapper.toDomain(defaultUserEntity.get());
        } else {
            throw new DefaultUserNotFoundException();
        }
    }

    @Transactional
    public DefaultUser createOrUpdateDefaultUser(UserEntity user, CreateUpdateDefaultUserDto data) {
        var userEntityOptional = repository.findByUser(user);

        DefaultUserEntity entity;

        if (userEntityOptional.isPresent()) {
            entity = userEntityOptional.get();
            entity.setName(data.name());
            entity.setBio(data.bio());
        } else {
            entity = new DefaultUserEntity(data);
            entity.setUser(user);
        }
        var saved = repository.save(entity);
        return DefaultUserMapper.toDomain(saved);
    }
}
