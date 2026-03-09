package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HlsWebMvcConfigurer implements WebMvcConfigurer {

    @Value("${live.stream.output-dir}")
    private String outputDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/live/**")
                .addResourceLocations("file:" + outputDir + "/");
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
            .mediaType("m3u8", MediaType.parseMediaType("application/vnd.apple.mpegurl"))
            .mediaType("ts",   MediaType.parseMediaType("video/mp2t"));
    }
}
