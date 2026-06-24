package com.hotel.room_service.controller;

import com.hotel.room_service.application.FechasAccesoUseCase;
import com.hotel.room_service.application.transformer.FechaAccesoTransformer;
import com.hotel.room_service.domain.model.HabitacionAccesoRequest;
import com.hotel.room_service.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FechasAccesoHandler {
    private final FechasAccesoUseCase fechasAccesoUseCase;

    public Mono<ServerResponse> consultaFechasAcceso(ServerRequest request) {
        return request.bodyToMono(HabitacionAccesoRequest.class)
                .flatMap(fechasAccesoUseCase::consultaFechaAcceso)
                .map(FechaAccesoTransformer::toResponse)
                .flatMap(fechas -> ServerResponse.ok()
                        .bodyValue(ApiResponse.ok(
                                "Fechas de acceso consultadas exitosamente",
                                fechas,
                                1
                        ))
                );
    }
}
