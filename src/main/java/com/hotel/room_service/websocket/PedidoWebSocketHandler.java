package com.hotel.room_service.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.room_service.application.usecase.PedidoNotificationUseCase;
import com.hotel.room_service.shared.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.CloseStatus;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoWebSocketHandler implements WebSocketHandler {

    private final PedidoNotificationUseCase notificationUseCase;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(@NonNull WebSocketSession session) {
        String token = extraerToken(session);

        if (token == null) {
            log.warn("WebSocket sin token - cerrando sesión");
            return session.close(CloseStatus.NOT_ACCEPTABLE);
        }

        return jwtService.validarToken(token)
                .flatMap(auth -> {
                    log.info("WebSocket autenticado: {}", auth.getName());
                    return session.send(
                            notificationUseCase.events()
                                    .handle((event, sink) -> {
                                        try {
                                            sink.next(session.textMessage(
                                                    objectMapper.writeValueAsString(event)
                                            ));
                                        } catch (Exception e) {
                                            sink.error(new RuntimeException(e));
                                        }
                                    })
                    );
                })
                .onErrorResume(ex -> {
                    log.error("Token WebSocket inválido: {}", ex.getMessage());
                    return session.close(CloseStatus.NOT_ACCEPTABLE);
                });
    }

    private String extraerToken(WebSocketSession session) {
        String query = session.getHandshakeInfo().getUri().getQuery();
        if (query == null) return null;

        return Arrays.stream(query.split("&"))
                .filter(param -> param.startsWith("token="))
                .map(param -> param.substring("token=".length()))
                .findFirst()
                .orElse(null);
    }
}
