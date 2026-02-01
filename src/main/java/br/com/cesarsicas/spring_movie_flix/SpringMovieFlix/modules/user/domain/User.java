package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.domain;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.data.UserEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.api.dto.UserDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user_auth.api.dto.RegisterDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public record User(long id, String login, String password, Role role, boolean isActive) {

    public User(UserEntity userEntity) {
        this(userEntity.getId(), userEntity.getLogin(), "", userEntity.getRole(), userEntity.isActive());
    }

    public User(UserDto userDto) {
        this(userDto.id(), userDto.email(), new BCryptPasswordEncoder().encode(userDto.password()), userDto.role(), userDto.isActive());
    }

    public User(RegisterDto registerDto) {
        this(0,
                registerDto.login(),
                new BCryptPasswordEncoder().encode(registerDto.password()), registerDto.role(), true);
    }
}

