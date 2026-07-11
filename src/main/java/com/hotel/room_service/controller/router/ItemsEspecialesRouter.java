package com.hotel.room_service.controller.router;

import com.hotel.room_service.controller.handler.ItemsEspecialesHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class ItemsEspecialesRouter {

    @Bean
    public RouterFunction<ServerResponse> consultaItemsEspecialesRoutes(ItemsEspecialesHandler handler) {
        return RouterFunctions
                .route(GET("/api/v1/items-especiales"), request -> handler.consultarItemsEspeciales());
    }
}
