package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.service;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleDetailsRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleReleaseEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleReleaseRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.*;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleReleaseEntity;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleReleaseRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.local.TitleDetailsRepository;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.ReleaseMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.ReleaseResponseMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.ReleaseToTitleSearchMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.TitleDetailsEntityMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.TitleDetailsResponseMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.mappers.TitleSearchMapper;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.WatchModeApiService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.Release;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleDetails;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.domain.TitleSearch;
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

    public List<TitleSearch> searchTitles(String query) {
        return watchModeApi.getTitleSearch(query).results().stream()
                .map(TitleSearchMapper::toDomain)
                .toList();
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
