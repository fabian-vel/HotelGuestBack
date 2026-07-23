package com.hotel.room_service.controller.handler;

import com.hotel.room_service.application.transformer.ConsultaPedidoTransformer;
import com.hotel.room_service.application.usecase.ConsultaPedidoUseCase;
import com.hotel.room_service.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ConsultaPedidoHandler {
    private final ConsultaPedidoUseCase consultaPedidoUseCase;

    public Mono<ServerResponse> consultarPedidoPorUsuario() {
        return consultaPedidoUseCase.consultarPedidoPorUsuario()
                .map(pedidos -> pedidos.stream()
                        .map(ConsultaPedidoTransformer::toResponse)
                        .toList()
                )
                .flatMap(pedidos -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ApiResponse.ok(
                                "Pedidos consultados exitosamente",
                                pedidos,
                                pedidos.size()
                        ))
                );
    }

    public Mono<ServerResponse> consultarPedidoFechaActual() {
        return consultaPedidoUseCase.consultarPedidoFechaActual()
                .map(pedidos -> pedidos.stream()
                        .map(ConsultaPedidoTransformer::toResponse)
                        .toList()
                )
                .flatMap(pedidos -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ApiResponse.ok(
                                "Pedidos del dia consultados exitosamente",
                                pedidos,
                                pedidos.size()
                        ))
                );
    }
}
