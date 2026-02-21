package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.api;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.service.DefaultUserService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.api.dto.GetDefaultUserDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.api.dto.CreateUpdateDefaultUserDto;
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
            var defaultUser = service.getDefaultUser(user);
            return ResponseEntity.ok(new GetDefaultUserDto(defaultUser));
        } catch (DefaultUserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/me")
    public ResponseEntity<GetDefaultUserDto> postMe(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody @Valid CreateUpdateDefaultUserDto data) {
        try {
            var defaultUser = service.createOrUpdateDefaultUser(user, data);
            return ResponseEntity.ok(new GetDefaultUserDto(defaultUser));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
