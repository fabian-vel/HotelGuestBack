package com.hotel.room_service.controller.handler;

import com.hotel.room_service.application.usecase.MenuCategoriaUseCase;
import com.hotel.room_service.application.transformer.MenuCategoriaTransformer;
import com.hotel.room_service.shared.constant.SuccessMessages;
import com.hotel.room_service.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MenuCategoriaHandler {

    private final MenuCategoriaUseCase menuCategoriaUseCase;

    public Mono<ServerResponse> consultarCategorias() {
        return menuCategoriaUseCase.consultarCategorias()
                .map(categorias -> categorias.stream()
                        .map(MenuCategoriaTransformer::toResponse)
                        .toList()
                )
                .flatMap(categorias -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ApiResponse.ok(
                                SuccessMessages.CATEGORIAS_CONSULTADAS.getMessage(),
                                categorias,
                                categorias.size()
                        ))
                );
    }
}
