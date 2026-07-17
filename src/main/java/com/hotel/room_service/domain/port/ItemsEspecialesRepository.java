package com.hotel.room_service.domain.port;

import com.hotel.room_service.domain.model.ItemEspecial;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ItemsEspecialesRepository {
    Mono<List<ItemEspecial>> consultarItemsMasPedidos(int cantidad);
    Mono<List<ItemEspecial>> consultarItemsMasRecientes(int cantidad);
    Mono<List<ItemEspecial>> consultarItemsRecomendadosChef(int cantidad, Short etiqueta);
}
