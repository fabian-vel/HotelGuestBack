package com.hotel.room_service.controller.handler;

import com.hotel.room_service.application.usecase.LoginEmpleadoUseCase;
import com.hotel.room_service.domain.model.EmpleadoLoginRequest;
import com.hotel.room_service.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EmpleadoAuthHandler {

    private final LoginEmpleadoUseCase loginEmpleadoUseCase;

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(EmpleadoLoginRequest.class)
                .flatMap(loginEmpleadoUseCase::ejecutar)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ApiResponse.ok("Login exitoso", response))
                );
    }
}