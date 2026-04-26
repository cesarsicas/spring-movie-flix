package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ChatService {

    @Value("${api.agent.url}")
    private String agentUrl;

    @Autowired
    private ObjectMapper objectMapper;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    public SseEmitter streamChat(String userId, String message) {
        SseEmitter emitter = new SseEmitter(0L);

        CompletableFuture.runAsync(() -> {
            try {
                String body = objectMapper.writeValueAsString(
                        Map.of("user_id", userId, "session_id", userId, "message", message)
                );

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(agentUrl + "/chat"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();

                HttpResponse<java.util.stream.Stream<String>> response =
                        httpClient.send(request, HttpResponse.BodyHandlers.ofLines());

                AtomicReference<String> eventName = new AtomicReference<>();
                response.body().forEach(line -> {
                    try {
                        if (line.startsWith("event:")) {
                            eventName.set(line.substring(6).trim());
                        } else if (line.startsWith("data:")) {
                            String data = line.substring(5).trim();
                            SseEmitter.SseEventBuilder event = SseEmitter.event()
                                    .data(data, MediaType.APPLICATION_JSON);
                            String name = eventName.getAndSet(null);
                            if (name != null) event.name(name);
                            emitter.send(event);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
