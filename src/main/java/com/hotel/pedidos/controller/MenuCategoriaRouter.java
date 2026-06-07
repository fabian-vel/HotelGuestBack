package com.hotel.pedidos.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

/**
 * Enrutador funcional WebFlux.
 * Define las rutas sin @RestController; cada ruta mapea a un método del handler.
 */
@Configuration
public class MenuCategoriaRouter {

    @Bean
    public RouterFunction<ServerResponse> categoriasRoutes(MenuCategoriaHandler handler) {
        return RouterFunctions
                // GET /api/v1/categorias
                // Query param opcional: ?parentIds=1,2,3
                .route(GET("/api/v1/categorias"), handler::consultarCategorias);
    }
}

