package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "genres", uniqueConstraints = @UniqueConstraint(columnNames = "external_id"))
@Entity(name = "Genre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false, unique = true)
    private int externalId;

    private String name;
    private int tmdb_id;
}
