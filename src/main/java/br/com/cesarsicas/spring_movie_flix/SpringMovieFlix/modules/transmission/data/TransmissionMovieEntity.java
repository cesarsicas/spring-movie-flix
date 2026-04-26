package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.data;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transmission_movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TransmissionMovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer duration;

    private String filename;
}
