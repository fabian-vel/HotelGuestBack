package com.hotel.room_service.controller.handler;

import com.hotel.room_service.application.transformer.ItemsEspecialesTransformer;
import com.hotel.room_service.application.usecase.ItemsEspecialesUseCase;
import com.hotel.room_service.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ItemsEspecialesHandler {
    private final ItemsEspecialesUseCase itemsEspecialesUseCase;

    public Mono<ServerResponse> consultarItemsEspeciales() {
        return itemsEspecialesUseCase.consultarItemsEspeciales()
                .map(ItemsEspecialesTransformer::toResponse)
                .flatMap( items -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ApiResponse.ok(
                                "",
                                items,
                                1
                        ))
                );
    }
}
