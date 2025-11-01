package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.user.dto;


import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.user.Role;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.user.User;
import jakarta.validation.constraints.NotEmpty;

public record UserDto(
        Long id,
        @NotEmpty
        String email,
        @NotEmpty
        String password,
        Role role,
        boolean isActive) {

   public UserDto(User user){
        this(user.id(), user.login(), "", user.role(), user.isActive());
    }
}
