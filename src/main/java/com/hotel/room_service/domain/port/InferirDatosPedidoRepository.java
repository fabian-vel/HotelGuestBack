package com.hotel.room_service.domain.port;

import com.hotel.room_service.domain.model.DatosPedido;
import reactor.core.publisher.Mono;

import java.util.List;

public interface InferirDatosPedidoRepository {
    Mono<List<DatosPedido>> inferirDatosPedidos(Short haacId, List<Short> listItemId);
}
