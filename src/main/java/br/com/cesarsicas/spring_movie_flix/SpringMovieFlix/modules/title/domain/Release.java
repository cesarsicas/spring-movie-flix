package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Release {
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

    public Release() {}

    public Release(int id, String title, String type, String imdb_id, Integer tmdb_id, String tmdb_type, Integer season_number, String poster_url, String source_release_date, Integer source_id, String source_name, Integer is_original) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.imdb_id = imdb_id;
        this.tmdb_id = tmdb_id;
        this.tmdb_type = tmdb_type;
        this.season_number = season_number;
        this.poster_url = poster_url;
        this.source_release_date = source_release_date;
        this.source_id = source_id;
        this.source_name = source_name;
        this.is_original = is_original;
    }

}
