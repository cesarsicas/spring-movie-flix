package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.default_user;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.default_user.dto.DefaultUserDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.exceptions.DefaultUserNotFoundException;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.exceptions.PermissionException;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.title.TitlesService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.user.data.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/default")
public class DefaultUserController {

    @Autowired
    DefaultUserService service;

    @GetMapping("/me")
    public ResponseEntity<DefaultUserDto> getMe(@AuthenticationPrincipal UserEntity user) {
        try {
            return ResponseEntity.ok(service.getDefaultUser(user));
        }
        catch (DefaultUserNotFoundException e){
            return  ResponseEntity.notFound().build();
        }
    }
}
