package com.hotel.room_service.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class MenuCategoriaRouter {

    @Bean
    public RouterFunction<ServerResponse> categoriasRoutes(MenuCategoriaHandler handler) {
        return RouterFunctions
                .route(GET("/api/v1/categorias"), request -> handler.consultarCategorias());
    }
}
