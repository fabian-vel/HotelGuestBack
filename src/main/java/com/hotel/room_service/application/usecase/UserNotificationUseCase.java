package com.hotel.room_service.application.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.room_service.domain.model.PedidoEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserNotificationUseCase {
    private final Map<Short, WebSocketSession> sessions =
            new ConcurrentHashMap<>();

    public void register(Short haacId,
                         WebSocketSession session) {

        sessions.put(haacId, session);

    }

    public void unregister(Short haacId) {

        sessions.remove(haacId);

    }

    public Mono<Void> send(Long haacId,
                           PedidoEvent<?> event,
                           ObjectMapper objectMapper) {

        WebSocketSession session = sessions.get(haacId);

        if (session == null || !session.isOpen()) {
            return Mono.empty();
        }

        try {

            String json =
                    objectMapper.writeValueAsString(event);

            return session.send(
                    Mono.just(session.textMessage(json))
            );

        } catch (Exception e) {

            return Mono.error(e);

        }

    }
}
