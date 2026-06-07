package com.hotel.pedidos.domain.port;

import com.hotel.pedidos.domain.model.MenuCategoria;
import reactor.core.publisher.Flux;

public interface MenuCategoriaRepository {
    Flux<MenuCategoria> consultarCategorias(String parentIds);
}

