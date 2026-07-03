package com.hotel.room_service.infrastructure.persistence;

import com.hotel.room_service.domain.model.Pedido;
import com.hotel.room_service.domain.port.InsertarPedidoRepository;
import com.hotel.room_service.shared.util.AdapterErrorUtil;
import com.hotel.room_service.shared.util.SqlLoaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class InsertarPedidoAdapter implements InsertarPedidoRepository {
    private final DatabaseClient databaseClient;
    private final String sql = SqlLoaderUtil.load("querys/insertar_pedido.sql");

    @Override
    public Mono<Long> insertarPedido(Pedido pedido) {
        return databaseClient.sql(sql)
                .bind("haacId", pedido.getHaacId())
                .bind("habitacion", pedido.getHabitacion())
                .bind("clienteNombre", pedido.getClienteNombre())
                .bind("estadoPedidoId", pedido.getEstadoPedidoId())
                .bind("observacion", pedido.getObservacion())
                .bind("total", pedido.getTotal())
                .map((row, metadata) -> row.get("pedi_id", Long.class))
                .one()
                .onErrorResume(AdapterErrorUtil.mapError("Error al insertar pedido en BD"));
    }
}
