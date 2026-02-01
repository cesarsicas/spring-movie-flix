package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.dto.GetDefaultUserDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.dto.CreateUpdateDefaultUserDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.shared.exceptions.DefaultUserNotFoundException;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.data.UserEntity;
import jakarta.validation.Valid;
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
    public ResponseEntity<GetDefaultUserDto> getMe(@AuthenticationPrincipal UserEntity user) {
        try {
            return ResponseEntity.ok(service.getDefaultUser(user));
        }
        catch (DefaultUserNotFoundException e){
            return  ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/me")
    public ResponseEntity postMe(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody @Valid CreateUpdateDefaultUserDto data) {
        try {
            var defaultUserDto = service.createOrUpdateDefaultUser(user, data);
            return ResponseEntity.ok(defaultUserDto);
        }
        catch (Exception e){
            return  ResponseEntity.internalServerError().build();
        }
    }
}
