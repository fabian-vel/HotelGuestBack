package com.hotel.room_service.domain.port;

import com.hotel.room_service.domain.model.DetallePedido;
import reactor.core.publisher.Mono;

import java.util.List;

public interface InsertarDetallePedidoRepository {
    Mono<Void> insertarDetallePedido(List<DetallePedido> detallePedidoList);
}
