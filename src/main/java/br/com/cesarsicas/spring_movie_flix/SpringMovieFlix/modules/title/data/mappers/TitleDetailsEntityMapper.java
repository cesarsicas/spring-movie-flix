package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.CastCrewMemberEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleDetailsEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleSourceEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.CastCrewMember;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleDetails;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleSource;

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
                Optional.ofNullable(entity.getRelevance_percentile()),
                toOptionalList(entity.getSources()).map(list -> list.stream().map(TitleDetailsEntityMapper::toSourceDomain).toList()),
                toOptionalList(entity.getCast()).map(list -> list.stream().map(TitleDetailsEntityMapper::toCastCrewDomain).toList())
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

        domain.sources().ifPresent(list -> entity.setSources(
                list.stream().map(s -> toSourceEntity(s, entity)).toList()));
        domain.cast().ifPresent(list -> entity.setCast(
                list.stream().map(c -> toCastCrewEntity(c, entity)).toList()));

        return entity;
    }

    private static TitleSource toSourceDomain(TitleSourceEntity e) {
        return new TitleSource(e.getSource_id(), e.getName(), e.getType(), e.getRegion(),
                e.getIos_url(), e.getAndroid_url(), e.getWeb_url(), e.getFormat(),
                e.getPrice(), e.getSeasons(), e.getEpisodes());
    }

    private static CastCrewMember toCastCrewDomain(CastCrewMemberEntity e) {
        return new CastCrewMember(e.getPerson_id(), e.getType(), e.getFull_name(),
                e.getHeadshot_url(), e.getRole(), e.getEpisode_count(), e.getOrder());
    }

    private static TitleSourceEntity toSourceEntity(TitleSource s, TitleDetailsEntity parent) {
        var e = new TitleSourceEntity();
        e.setTitleDetails(parent);
        e.setSource_id(s.source_id());
        e.setName(s.name());
        e.setType(s.type());
        e.setRegion(s.region());
        e.setIos_url(s.ios_url());
        e.setAndroid_url(s.android_url());
        e.setWeb_url(s.web_url());
        e.setFormat(s.format());
        e.setPrice(s.price());
        e.setSeasons(s.seasons());
        e.setEpisodes(s.episodes());
        return e;
    }

    private static CastCrewMemberEntity toCastCrewEntity(CastCrewMember c, TitleDetailsEntity parent) {
        var e = new CastCrewMemberEntity();
        e.setTitleDetails(parent);
        e.setPerson_id(c.person_id());
        e.setType(c.type());
        e.setFull_name(c.full_name());
        e.setHeadshot_url(c.headshot_url());
        e.setRole(c.role());
        e.setEpisode_count(c.episode_count());
        e.setOrder(c.order());
        return e;
    }

    @SuppressWarnings("unchecked")
    private static <T> Optional<List<T>> toOptionalList(List<T> list) {
        if (list == null || list.isEmpty()) return Optional.empty();
        return Optional.of(list);
    }
}
