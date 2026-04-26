package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.chat.api;

import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.chat.api.dto.ChatRequestDto;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.chat.service.ChatService;
import br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.user.data.UserEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/default/chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chat(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody @Valid ChatRequestDto dto
    ) {
        return chatService.streamChat(user.getId().toString(), dto.message());
    }
}
