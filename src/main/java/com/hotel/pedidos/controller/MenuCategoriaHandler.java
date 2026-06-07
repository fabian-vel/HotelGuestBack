package com.hotel.pedidos.controller;

import com.hotel.pedidos.application.ConsultarCategoriasUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MenuCategoriaHandler {

    private final ConsultarCategoriasUseCase consultarCategoriasUseCase;

    public Mono<ServerResponse> consultarCategorias(ServerRequest request) {

        String parentIds = request.queryParam("parentIds").orElse(null);

        return consultarCategoriasUseCase.ejecutar(parentIds)
                .collectList()
                .flatMap(categorias -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(categorias))
                .onErrorResume(ex -> ServerResponse
                        .status(500)
                        .bodyValue("Error al consultar categorías: " + ex.getMessage()));
    }
}

