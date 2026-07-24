package com.hotel.room_service.websocket;

import com.hotel.room_service.application.usecase.UserNotificationUseCase;
import com.hotel.room_service.shared.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserWebSocketHandler implements WebSocketHandler {

    private final TokenUtil tokenUtil;
    private final UserNotificationUseCase notificationUseCase;

    @Override
    public Mono<Void> handle(WebSocketSession session) {

        return tokenUtil.getHaacId()
                .flatMap(haacId -> {

                    notificationUseCase.register(
                            haacId,
                            session
                    );

                    return session.receive()
                            .doFinally(signal ->
                                    notificationUseCase.unregister(haacId)
                            )
                            .then();
                });
    }
}
