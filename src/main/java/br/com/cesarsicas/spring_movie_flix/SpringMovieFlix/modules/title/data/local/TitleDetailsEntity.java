package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.converters.ListIntegerJsonConverter;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.converters.ListStringJsonConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "title_details", uniqueConstraints = @UniqueConstraint(columnNames = "external_id"))
@Entity(name = "TitleDetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TitleDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false, unique = true)
    private Long externalId;

    private String title;
    private String original_title;
    private String plot_overview;
    private String type;
    private Integer runtime_minutes;
    private Integer year;
    private Integer end_year;
    private String release_date;
    private String imdb_id;
    private Integer tmdb_id;
    private String tmdb_type;

    @Convert(converter = ListIntegerJsonConverter.class)
    @Column(columnDefinition = "text")
    private List<Integer> genres;

    @Convert(converter = ListStringJsonConverter.class)
    @Column(columnDefinition = "text")
    private List<String> genre_names;

    private Double user_rating;
    private Integer critic_score;
    private String us_rating;
    private String poster;
    private String backdrop;
    private String original_language;

    @Convert(converter = ListIntegerJsonConverter.class)
    @Column(columnDefinition = "text")
    private List<Integer> similar_titles;

    @Convert(converter = ListIntegerJsonConverter.class)
    @Column(columnDefinition = "text")
    private List<Integer> networks;

    @Convert(converter = ListStringJsonConverter.class)
    @Column(columnDefinition = "text")
    private List<String> network_names;

    private String trailer;
    private String trailer_thumbnail;
    private Double relevance_percentile;
}
