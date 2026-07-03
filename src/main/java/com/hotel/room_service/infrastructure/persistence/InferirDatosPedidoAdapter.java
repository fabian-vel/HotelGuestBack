package com.hotel.room_service.infrastructure.persistence;

import com.hotel.room_service.domain.model.DatosPedido;
import com.hotel.room_service.domain.port.InferirDatosPedidoRepository;
import com.hotel.room_service.infrastructure.mapper.DatosPedidosRowMapper;
import com.hotel.room_service.shared.util.AdapterErrorUtil;
import com.hotel.room_service.shared.util.SqlLoaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InferirDatosPedidoAdapter implements InferirDatosPedidoRepository {
    private final DatabaseClient databaseClient;
    private final DatosPedidosRowMapper datosPedidosRowMapper;
    private final String sql = SqlLoaderUtil.load("querys/inferir-datos-pedido.sql");

    @Override
    public Mono<List<DatosPedido>> inferirDatosPedidos(Short haacId, List<Short> listItemId) {
        return databaseClient.sql(sql)
                .bind("haacId", haacId)
                .bind("meitIds", listItemId)
                .map((row, metadata) -> datosPedidosRowMapper.mapRow(row))
                .all()
                .collectList()
                .onErrorResume(AdapterErrorUtil.mapError("Error al inferir datos de pedidos en BD"));
    }
}
