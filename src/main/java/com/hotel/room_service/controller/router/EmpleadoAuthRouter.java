package com.hotel.room_service.controller.router;

import com.hotel.room_service.controller.handler.EmpleadoAuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class EmpleadoAuthRouter {

    @Bean
    public RouterFunction<ServerResponse> empleadoAuthRoutes(EmpleadoAuthHandler handler) {
        return RouterFunctions
                .route(POST("/api/v1/auth/empleado/login"), handler::login);
    }
}
