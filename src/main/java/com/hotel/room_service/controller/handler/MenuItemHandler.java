package com.hotel.room_service.controller.handler;

import com.hotel.room_service.application.usecase.MenuItemUseCase;
import com.hotel.room_service.application.transformer.MenuItemTransformer;
import com.hotel.room_service.domain.model.MenuItemRequest;
import com.hotel.room_service.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MenuItemHandler {

    private final MenuItemUseCase menuItemUseCase;

    public Mono<ServerResponse> consultaItemsPorCategoria(ServerRequest request) {
        return request.bodyToMono(MenuItemRequest.class)
                .flatMap(menuItemUseCase::consultarItemsPorCategoria)
                .map(items -> items.stream()
                        .map(MenuItemTransformer::toResponse)
                        .toList()
                )
                .flatMap(items -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ApiResponse.ok(
                                "Menu consultados exitosamente",
                                items,
                                items.size()
                        ))
                );
    }
}
