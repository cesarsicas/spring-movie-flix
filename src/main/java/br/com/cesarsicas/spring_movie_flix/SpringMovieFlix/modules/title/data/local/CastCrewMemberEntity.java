package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "title_cast_crew")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CastCrewMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_details_id", nullable = false)
    private TitleDetailsEntity titleDetails;

    private long person_id;
    private String type;
    private String full_name;

    @Column(columnDefinition = "text")
    private String headshot_url;

    @Column(columnDefinition = "text")
    private String role;

    private int episode_count;

    @Column(name = "cast_order")
    private int order;
}
