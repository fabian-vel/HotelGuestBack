package com.hotel.room_service.domain.port;

import com.hotel.room_service.domain.model.Pedido;
import reactor.core.publisher.Mono;

public interface InsertarPedidoRepository {
    Mono<Long> insertarPedido(Pedido pedido);
}
