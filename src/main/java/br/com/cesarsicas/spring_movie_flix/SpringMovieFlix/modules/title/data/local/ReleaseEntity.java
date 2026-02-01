package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Release;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "releases")
@Entity(name = "release")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ReleaseEntity {

    @Id
    private int id;
    private String title;
    private String type;
    private String imdb_id;
    private Integer tmdb_id;
    private String tmdb_type;
    private Integer season_number;
    private String poster_url;
    private String source_release_date;
    private Integer source_id;
    private String source_name;
    private Integer is_original;

    public ReleaseEntity(Release release) {
        this.id = release.getId();
        this.title = release.getTitle();
        this.type = release.getType();
        this.imdb_id = release.getImdb_id();
        this.tmdb_id = release.getTmdb_id();
        this.tmdb_type = release.getTmdb_type();
        this.season_number = release.getSeason_number();
        this.poster_url = release.getPoster_url();
        this.source_release_date = release.getSource_release_date();
        this.source_id = release.getSource_id();
        this.source_name = release.getSource_name();
        this.is_original = release.getIs_original();
    }
}
