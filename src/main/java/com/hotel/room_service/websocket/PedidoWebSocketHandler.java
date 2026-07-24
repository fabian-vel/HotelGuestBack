package com.hotel.room_service.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.room_service.application.usecase.PedidoNotificationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PedidoWebSocketHandler implements WebSocketHandler {
    private final PedidoNotificationUseCase notificationUseCase;

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(WebSocketSession session) {

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
    }
}
