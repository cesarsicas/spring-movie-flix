package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.user;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.user.data.UserEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.user.dto.UserDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.user_auth.dto.RegisterDto;
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

