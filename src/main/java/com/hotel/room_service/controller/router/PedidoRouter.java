package com.hotel.room_service.controller.router;

import com.hotel.room_service.controller.handler.PedidoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class PedidoRouter {

    @Bean
    public RouterFunction<ServerResponse> pedidoRoutes(PedidoHandler pedidoHandler) {
        return RouterFunctions
                .route(POST("/api/v1/pedido"), pedidoHandler::crearPedido);
    }
}
