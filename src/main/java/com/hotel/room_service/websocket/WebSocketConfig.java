package com.hotel.room_service.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class WebSocketConfig {

    private final PedidoWebSocketHandler handler;

    @Bean
    HandlerMapping webSocketMapping() {

        SimpleUrlHandlerMapping mapping =
                new SimpleUrlHandlerMapping();
        mapping.setOrder(1);
        mapping.setUrlMap(
                Map.of(
                        "/ws/pedidos",
                        handler
                )
        );
        return mapping;
    }

    @Bean
    WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
