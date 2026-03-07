package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.transmission.data;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transmissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TransmissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String movieName;

    private LocalDateTime startTime;

    private Integer duration;

    private boolean isActive;
}
