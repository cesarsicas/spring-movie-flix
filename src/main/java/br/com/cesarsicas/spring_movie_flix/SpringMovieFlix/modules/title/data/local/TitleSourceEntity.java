package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "title_sources")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TitleSourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_details_id", nullable = false)
    private TitleDetailsEntity titleDetails;

    private int source_id;
    private String name;
    private String type;
    private String region;

    @Column(columnDefinition = "text")
    private String ios_url;

    @Column(columnDefinition = "text")
    private String android_url;

    @Column(columnDefinition = "text")
    private String web_url;

    private String format;
    private Double price;
    private Integer seasons;
    private Integer episodes;
}
