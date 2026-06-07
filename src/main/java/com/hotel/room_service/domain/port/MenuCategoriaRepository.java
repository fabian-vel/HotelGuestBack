package com.hotel.room_service.domain.port;

import com.hotel.room_service.domain.model.MenuCategoria;
import reactor.core.publisher.Flux;

public interface MenuCategoriaRepository {
    Flux<MenuCategoria> consultarCategorias(String parentIds);
}

