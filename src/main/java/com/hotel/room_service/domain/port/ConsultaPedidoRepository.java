package com.hotel.room_service.domain.port;

import com.hotel.room_service.domain.model.ConsultaPedido;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ConsultaPedidoRepository {
    Mono<List<ConsultaPedido>> consultarPedido(Short haacId);
}
