package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleDetailsEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleDetails;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TitleDetailsEntityMapper {

    public static TitleDetails toDomain(TitleDetailsEntity entity) {
        if (entity == null) return null;
        return new TitleDetails(
            entity.getId(),
            entity.getExternalId(),
                entity.getTitle(),
                entity.getOriginal_title(),
                entity.getPlot_overview(),
                entity.getType(),
                Optional.ofNullable(entity.getRuntime_minutes()),
                Optional.ofNullable(entity.getYear()),
                Optional.ofNullable(entity.getEnd_year()),
                Optional.ofNullable(entity.getRelease_date()),
                Optional.ofNullable(entity.getImdb_id()),
                Optional.ofNullable(entity.getTmdb_id()),
                Optional.ofNullable(entity.getTmdb_type()),
                toOptionalList(entity.getGenres()),
                toOptionalList(entity.getGenre_names()),
                Optional.ofNullable(entity.getUser_rating()),
                Optional.ofNullable(entity.getCritic_score()),
                Optional.ofNullable(entity.getUs_rating()),
                Optional.ofNullable(entity.getPoster()),
                Optional.ofNullable(entity.getBackdrop()),
                Optional.ofNullable(entity.getOriginal_language()),
                toOptionalList(entity.getSimilar_titles()),
                toOptionalList(entity.getNetworks()),
                toOptionalList(entity.getNetwork_names()),
                Optional.ofNullable(entity.getTrailer()),
                Optional.ofNullable(entity.getTrailer_thumbnail()),
                Optional.ofNullable(entity.getRelevance_percentile())
        );
    }

    public static TitleDetailsEntity toEntity(TitleDetails domain) {
        if (domain == null) return null;
        var entity = new TitleDetailsEntity();
        entity.setExternalId(domain.externalId());
        entity.setTitle(domain.title());
        entity.setOriginal_title(domain.original_title());
        entity.setPlot_overview(domain.plot_overview());
        entity.setType(domain.type());
        entity.setRuntime_minutes(domain.runtime_minutes().orElse(null));
        entity.setYear(domain.year().orElse(null));
        entity.setEnd_year(domain.end_year().orElse(null));
        entity.setRelease_date(domain.release_date().orElse(null));
        entity.setImdb_id(domain.imdb_id().orElse(null));
        entity.setTmdb_id(domain.tmdb_id().orElse(null));
        entity.setTmdb_type(domain.tmdb_type().orElse(null));
        entity.setGenres(domain.genres().orElse(Collections.emptyList()));
        entity.setGenre_names(domain.genre_names().orElse(Collections.emptyList()));
        entity.setUser_rating(domain.user_rating().orElse(null));
        entity.setCritic_score(domain.critic_score().orElse(null));
        entity.setUs_rating(domain.us_rating().orElse(null));
        entity.setPoster(domain.poster().orElse(null));
        entity.setBackdrop(domain.backdrop().orElse(null));
        entity.setOriginal_language(domain.original_language().orElse(null));
        entity.setSimilar_titles(domain.similar_titles().orElse(Collections.emptyList()));
        entity.setNetworks(domain.networks().orElse(Collections.emptyList()));
        entity.setNetwork_names(domain.network_names().orElse(Collections.emptyList()));
        entity.setTrailer(domain.trailer().orElse(null));
        entity.setTrailer_thumbnail(domain.trailer_thumbnail().orElse(null));
        entity.setRelevance_percentile(domain.relevance_percentile().orElse(null));
        return entity;
    }

    @SuppressWarnings("unchecked")
    private static <T> Optional<List<T>> toOptionalList(List<T> list) {
        if (list == null || list.isEmpty()) return Optional.empty();
        return Optional.of(list);
    }
}
