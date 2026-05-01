package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "title_list_items", uniqueConstraints = @UniqueConstraint(columnNames = "external_id"))
@Entity(name = "TitleListItem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TitleListItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false, unique = true)
    private Long externalId;

    private String name;
    private String type;
    private Integer year;
    private String imdb_id;
    private Integer tmdb_id;
    private String tmdb_type;
}
