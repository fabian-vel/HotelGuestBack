package com.hotel.room_service.controller.handler;

import com.hotel.room_service.application.usecase.PedidoUseCase;
import com.hotel.room_service.domain.model.PedidoCriterio;
import com.hotel.room_service.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PedidoHandler {

    private final PedidoUseCase pedidoUseCase;

    public Mono<ServerResponse> crearPedido(ServerRequest request) {
        return request.bodyToMono(PedidoCriterio.class)
                .flatMap(pedidoUseCase::crearPedido)
                .flatMap(pedido -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ApiResponse.ok(
                                "Pedido creado exitosamente",
                                pedido,
                                1
                        ))
                );
    }
}
