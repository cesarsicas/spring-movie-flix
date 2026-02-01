package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.dto.TitleReleasesDto;
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

    public ReleaseEntity(TitleReleasesDto titleReleasesDto) {
        this.id = titleReleasesDto.id();
        this.title = titleReleasesDto.title();
        this.type = titleReleasesDto.type();
        this.imdb_id = titleReleasesDto.imdb_id();
        this.tmdb_id = titleReleasesDto.tmdb_id();
        this.tmdb_type = titleReleasesDto.tmdb_type();
        this.season_number = titleReleasesDto.season_number();
        this.poster_url = titleReleasesDto.poster_url();
        this.source_release_date = titleReleasesDto.source_release_date();
        this.source_id = titleReleasesDto.source_id();
        this.source_name = titleReleasesDto.source_name();
        this.is_original = titleReleasesDto.is_original();
    }
}
