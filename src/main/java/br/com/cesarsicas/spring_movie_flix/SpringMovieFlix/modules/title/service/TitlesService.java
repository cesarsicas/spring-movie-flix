package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.service;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.GenreEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.GenreRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.PersonRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleDetailsRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleListItemEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleListItemRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleReleaseEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleReleaseRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.PersonEntityMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.PersonRemoteMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.ReleaseMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.ReleaseResponseMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.GenreMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.SearchResultMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.TitleDetailsEntityMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.TitleDetailsResponseMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.TitleListItemEntityMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.TitleListItemMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.WatchModeApiService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.AutocompleteFilterResultType;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.AutocompleteSearchResponse;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model.SearchField;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Genre;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Person;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Release;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.SearchResult;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleDetails;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleListItem;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleListPage;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.VideoChunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TitlesService {

    @Value("${video.base-path}")
    private String videoBasePath;

    @Autowired
    WatchModeApiService watchModeApi;

    @Autowired
    TitleReleaseRepository titleReleaseRepository;

    @Autowired
    TitleDetailsRepository titleDetailsRepository;

    @Autowired
    TitleListItemRepository titleListItemRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    GenreRepository genreRepository;

    public List<Release> getReleases(Boolean useCache) {
        if (useCache){
            return titleReleaseRepository.findAll().stream().map(ReleaseMapper::toDomain).toList();
        }

        var releases = watchModeApi.getReleases().releases().stream().map(ReleaseResponseMapper::toDomain)
                .toList();

        var externalIds = releases.stream().mapToLong(Release::getExternal_id).boxed().toList();
        var existingExternalIds = Set.copyOf(
                titleReleaseRepository.findExternal_idByExternal_idIn(externalIds));

        var newReleases = releases.stream()
                .filter(r -> !existingExternalIds.contains(r.getExternal_id()))
                .toList();
        var entities = newReleases.stream().map(TitleReleaseEntity::new).toList();
        titleReleaseRepository.saveAll(entities);

        // Ensure domain releases have the database id populated (for both newly saved and existing ones)
        releases.forEach(r ->
            titleReleaseRepository.findByExternal_id(r.getExternal_id())
                .ifPresent(e -> r.setId(e.getId()))
        );

        return releases;
    }

    public TitleDetails getTitleDetails(long externalId, Boolean useCache) {
        if (Boolean.TRUE.equals(useCache)) {
            Optional<TitleDetails> cached = titleDetailsRepository.findByExternalId(externalId)
                    .map(TitleDetailsEntityMapper::toDomain);
            if (cached.isPresent()) return cached.get();
        }
        var response = watchModeApi.getTitleDetails(externalId);
        var details = TitleDetailsResponseMapper.toDomain(response);
        var entity = TitleDetailsEntityMapper.toEntity(details);
        titleDetailsRepository.findByExternalId(externalId).ifPresent(existing -> entity.setId(existing.getId()));
        titleDetailsRepository.save(entity);
        return details;
    }

    public List<Genre> getGenres(Boolean useCache) {
        if (Boolean.TRUE.equals(useCache)) {
            return genreRepository.findAll().stream().map(GenreMapper::toDomain).toList();
        }

        var remote = watchModeApi.getGenres();
        var genres = remote.stream().map(GenreMapper::toDomain).toList();

        var existingIds = Set.copyOf(
                genreRepository.findExternalIdsByExternalIdIn(
                        genres.stream().map(Genre::externalId).toList()));

        var newEntities = genres.stream()
                .filter(g -> !existingIds.contains(g.externalId()))
                .map(GenreMapper::toEntity)
                .toList();
        genreRepository.saveAll(newEntities);

        return genreRepository.findAll().stream().map(GenreMapper::toDomain).toList();
    }

    public AutocompleteSearchResponse getAutocompleteSearch(String query, AutocompleteFilterResultType filterResultType) {
        return watchModeApi.getAutocompleteSearch(query, filterResultType);
    }

    public SearchResult searchTitlesAndPeople(String query, SearchField searchField, String types) {
        return SearchResultMapper.toDomain(watchModeApi.search(searchField, query, types));
    }

    public TitleListPage listTitles(
            String types, String sourceIds, String genres, String regions,
            String sourceTypes, String networkIds, String languages,
            Integer releaseDateStart, Integer releaseDateEnd,
            Double userRatingLow, Double userRatingHigh,
            Integer criticScoreLow, Integer criticScoreHigh,
            Integer personId, String sortBy, Integer page, Integer limit,
            Boolean useCache) {

        if (Boolean.TRUE.equals(useCache)) {
            // useCache=true returns all cached items; dynamic filter params cannot be replayed from DB
            var cached = titleListItemRepository.findAll().stream()
                    .map(TitleListItemEntityMapper::toDomain)
                    .toList();
            return new TitleListPage(cached, null, cached.size(), null);
        }

        var response = watchModeApi.listTitles(types, sourceIds, genres, regions, sourceTypes,
                networkIds, languages, releaseDateStart, releaseDateEnd,
                userRatingLow, userRatingHigh, criticScoreLow, criticScoreHigh,
                personId, sortBy, page, limit);

        List<TitleListItem> domainItems = response.titles().stream()
                .map(TitleListItemMapper::toDomain)
                .toList();

        var externalIds = domainItems.stream().map(TitleListItem::externalId).toList();
        var existingIds = Set.copyOf(titleListItemRepository.findExternalIdsByExternalIdIn(externalIds));

        var newEntities = domainItems.stream()
                .filter(i -> !existingIds.contains(i.externalId()))
                .map(TitleListItemEntityMapper::toEntity)
                .toList();
        titleListItemRepository.saveAll(newEntities);

        var enriched = domainItems.stream().map(item -> {
            Long dbId = titleListItemRepository.findByExternalId(item.externalId())
                    .map(TitleListItemEntity::getId)
                    .orElse(null);
            return new TitleListItem(dbId, item.externalId(), item.title(), item.type(),
                    item.year(), item.imdb_id(), item.tmdb_id(), item.tmdb_type());
        }).toList();

        return new TitleListPage(enriched, response.page(), response.total_results(), response.total_pages());
    }

    public Person getPerson(long personId, Boolean useCache) {
        if (Boolean.TRUE.equals(useCache)) {
            Optional<Person> cached = personRepository.findByExternalId(personId)
                    .map(PersonEntityMapper::toDomain);
            if (cached.isPresent()) return cached.get();
        }
        var remote = watchModeApi.getPerson(personId);
        var domain = PersonRemoteMapper.toDomain(remote);
        var entity = PersonEntityMapper.toEntity(domain);
        personRepository.findByExternalId(personId).ifPresent(existing -> entity.setId(existing.getId()));
        var saved = personRepository.save(entity);
        return PersonEntityMapper.toDomain(saved);
    }

    public Optional<VideoChunk> getVideoChunk(long start, long end) throws IOException {
        File file = new File(videoBasePath + "/video-streaming.mp4");
        if (!file.exists()) {
            return Optional.empty();
        }

        long fileSize = file.length();
        long rangeEnd = (end == -1) ? fileSize - 1 : end;
        long contentLength = rangeEnd - start + 1;
        byte[] data = new byte[(int) contentLength];

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            raf.seek(start);
            raf.readFully(data);
        }

        return Optional.of(new VideoChunk(data, start, rangeEnd, fileSize));
    }

}
