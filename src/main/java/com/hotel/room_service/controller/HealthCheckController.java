package com.hotel.room_service.controller;

import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class HealthCheckController {

    private final ConnectionFactory connectionFactory;

    @Bean
    public RouterFunction<ServerResponse> healthCheckRoute() {
        return RouterFunctions.route()
                .GET("/health", request -> checkDatabaseConnection())
                .build();
    }

    private Mono<ServerResponse> checkDatabaseConnection() {
        return Mono.from(connectionFactory.create())
                .flatMap(connection ->
                    Mono.from(connection.createStatement("SELECT 1 FROM MST_MENU_CATEGORIAS LIMIT 1").execute())
                        .flatMap(result -> Mono.from(result.getRowsUpdated()))
                        .then(Mono.from(connection.close()))
                )
                .then(ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(new HealthResponse("UP", "Servicio disponible")))
                .onErrorResume(err -> {
                    log.error("Health check fallido", err);
                    return ServerResponse.status(503)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(new HealthResponse("DOWN", "Servicio no disponible"));
                });
    }

    private record HealthResponse(String status, String message) {}
}
