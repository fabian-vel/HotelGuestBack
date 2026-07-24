package com.hotel.room_service.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class UserWebSocketConfig {
    private final UserWebSocketHandler handler;

    @Bean
    HandlerMapping userMapping() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(2);
        mapping.setUrlMap(
                Map.of(
                        "/ws/orders",
                        handler
                )
        );

        return mapping;
    }
}
