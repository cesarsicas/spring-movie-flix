package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.data;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.data.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefaultUserRepository extends JpaRepository<DefaultUserEntity, Long> {
    Optional<DefaultUserEntity> findByUser(UserEntity user);
}
