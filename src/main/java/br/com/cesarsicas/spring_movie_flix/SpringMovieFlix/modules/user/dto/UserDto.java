package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.dto;


import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.Role;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.User;
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
