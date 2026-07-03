package com.hotel.room_service.infrastructure.persistence;

import com.hotel.room_service.domain.model.DetallePedido;
import com.hotel.room_service.domain.port.InsertarDetallePedidoRepository;
import com.hotel.room_service.shared.util.AdapterErrorUtil;
import com.hotel.room_service.shared.util.SqlLoaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InsertarDetallePedidoAdapter implements InsertarDetallePedidoRepository {

    private final DatabaseClient databaseClient;
    private final String sql = SqlLoaderUtil.load("querys/insertar_detalle_pedido.sql");

    @Override
    public Mono<Void> insertarDetallePedido(List<DetallePedido> detallePedidoList) {
        return Flux.fromIterable(detallePedidoList)
                .flatMap(detallePedido ->
                        databaseClient.sql(sql)
                                .bind("pediId", detallePedido.getPediId())
                                .bind("meitId", detallePedido.getMeitId())
                                .bind("cantidad", detallePedido.getCantidad())
                                .bind("precioUnitario", detallePedido.getPrecioUnitario())
                                .bind("subtotal", detallePedido.getSubtotal())
                                .bind("observacion", detallePedido.getObservacion())
                                .fetch()
                                .rowsUpdated()
                )
                .then()
                .onErrorResume(AdapterErrorUtil.mapError("Error al insertar detalle pedido en BD"));
    }
}