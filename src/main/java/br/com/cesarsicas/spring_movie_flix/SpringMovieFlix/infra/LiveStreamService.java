package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.infra;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LiveStreamService {

    @Value("${live.stream.source}")
    private String sourceFile;

    @Value("${live.stream.output-dir}")
    private String outputDir;

    private Process ffmpegProcess;

    public void start() throws IOException {
        if (ffmpegProcess != null && ffmpegProcess.isAlive()) return;

        Path outPath = Paths.get(outputDir);
        Files.createDirectories(outPath);
        String playlistPath = outPath.resolve("stream.m3u8").toString();

        ProcessBuilder pb = new ProcessBuilder(
            "ffmpeg", "-re",
            "-i", sourceFile,
            "-c:v", "copy", "-c:a", "aac",
            "-f", "hls",
            "-hls_time", "2",
            "-hls_list_size", "5",
            "-hls_flags", "delete_segments",
            playlistPath
        );
        pb.redirectErrorStream(true);
        pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
        ffmpegProcess = pb.start();
    }

    public void stop() {
        if (ffmpegProcess != null && ffmpegProcess.isAlive()) {
            ffmpegProcess.destroy();
            ffmpegProcess = null;
        }
    }

    @PreDestroy
    public void cleanup() { stop(); }
}
