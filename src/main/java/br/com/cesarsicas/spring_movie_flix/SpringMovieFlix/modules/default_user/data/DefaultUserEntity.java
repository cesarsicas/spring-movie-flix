package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.data;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.default_user.api.dto.CreateUpdateDefaultUserDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.data.UserEntity;
import jakarta.persistence.*;
import lombok.*;


@Table(name = "default_user")
@Entity(name = "DefaultUser")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class DefaultUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String bio;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;


    public DefaultUserEntity( CreateUpdateDefaultUserDto defaultUserDto){
        this.name = defaultUserDto.name();
        this.bio = defaultUserDto.bio();
    }

}