package com.hotel.pedidos.application;

import com.hotel.pedidos.controller.dto.MenuCategoriaResponse;
import com.hotel.pedidos.domain.port.MenuCategoriaRepository;
import com.hotel.pedidos.infrastructure.mapper.MenuCategoriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ConsultarCategoriasUseCase {

    private final MenuCategoriaRepository menuCategoriaRepository;
    private final MenuCategoriaMapper     menuCategoriaMapper;

    public Flux<MenuCategoriaResponse> ejecutar(String parentIds) {
        return menuCategoriaRepository
                .consultarCategorias(parentIds)
                .map(menuCategoriaMapper::toResponse);
    }
}

