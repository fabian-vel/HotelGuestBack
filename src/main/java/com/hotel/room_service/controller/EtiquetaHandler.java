package com.hotel.room_service.controller;

import com.hotel.room_service.application.EtiquetaUseCase;
import com.hotel.room_service.application.transformer.EtiquetaTransformer;
import com.hotel.room_service.domain.model.EtiquetaRequest;
import com.hotel.room_service.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EtiquetaHandler {
    private final EtiquetaUseCase etiquetaUseCase;

    public Mono<ServerResponse> consultaEtiqueta(ServerRequest request) {
        return request.bodyToMono(EtiquetaRequest.class)
                .flatMap(etiquetaUseCase::consultaEtiqueta)
                .map(etiqueta -> etiqueta.stream()
                        .map(EtiquetaTransformer::toResponse)
                        .toList()
                )
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ApiResponse.ok(
                                "Etiquetas consultadas exitosamente",
                                response,
                                response.size()
                        ))
                );
    }
}
