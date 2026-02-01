package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user_auth.api;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.domain.User;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.service.UserService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.data.UserEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user_auth.service.TokenService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user_auth.api.dto.LoginDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user_auth.api.dto.RegisterDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user_auth.api.dto.TokenJWTDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenJWTDto> login(@RequestBody @Valid LoginDto data) {
        var token = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var authentication = manager.authenticate(token);

        var tokenJWT = tokenService.generateToken((UserEntity) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJWTDto(tokenJWT));

    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity register(@RequestBody @Valid RegisterDto registerDto) {
        userService.saveUser(new User(registerDto));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/test")
    public ResponseEntity test(){
        return ResponseEntity.ok().build();
    }
}