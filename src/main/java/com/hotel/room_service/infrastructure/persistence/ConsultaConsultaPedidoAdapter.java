package com.hotel.room_service.infrastructure.persistence;

import com.hotel.room_service.domain.model.ConsultaPedido;
import com.hotel.room_service.domain.port.ConsultaPedidoRepository;
import com.hotel.room_service.infrastructure.mapper.ConsultaPedidoRowMapper;
import com.hotel.room_service.shared.util.AdapterErrorUtil;
import com.hotel.room_service.shared.util.SqlLoaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConsultaConsultaPedidoAdapter implements ConsultaPedidoRepository {
    private final DatabaseClient databaseClient;
    private final ConsultaPedidoRowMapper consultaPedidoRowMapper;
    private final String sql = SqlLoaderUtil.load("querys/consulta_pedidos.sql");
    private final String sqlFechaActual = SqlLoaderUtil.load("querys/consulta-pedidos-actual.sql");

    @Override
    public Mono<List<ConsultaPedido>> consultarPedidoPorUsuario(Short haacId) {
        return databaseClient.sql(sql)
                .bind("haacId", haacId)
                .map((row, metadata) -> consultaPedidoRowMapper.mapRow(row))
                .all()
                .collectList()
                .onErrorResume(AdapterErrorUtil.mapError("Error al consultar pedidos en BD"));
    }

    @Override
    public Mono<List<ConsultaPedido>> consultarPedidoFechaActual() {
        return databaseClient.sql(sqlFechaActual)
                .map((row, metadata) -> consultaPedidoRowMapper.mapRow(row))
                .all()
                .collectList()
                .onErrorResume(AdapterErrorUtil.mapError("Error al consultar pedidos por fecha actual en BD"));
    }
}
