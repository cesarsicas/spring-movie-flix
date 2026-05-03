package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.converters.ListIntegerJsonConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "persons", uniqueConstraints = @UniqueConstraint(columnNames = "external_id"))
@Entity(name = "Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false, unique = true)
    private Long externalId;

    private String full_name;
    private String first_name;
    private String last_name;
    private Integer tmdb_id;
    private String imdb_id;
    private String main_profession;
    private String secondary_profession;
    private String tertiary_profession;
    private String date_of_birth;
    private String date_of_death;
    private String place_of_birth;
    private String gender;

    @Column(name = "headshot_url", length = 1000)
    private String headshot_url;

    @Convert(converter = ListIntegerJsonConverter.class)
    @Column(columnDefinition = "text")
    private List<Integer> known_for;

    private Double relevance_percentile;
}
