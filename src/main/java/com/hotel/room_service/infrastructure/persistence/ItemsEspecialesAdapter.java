package com.hotel.room_service.infrastructure.persistence;

import com.hotel.room_service.domain.model.ItemEspecial;
import com.hotel.room_service.domain.port.ItemsEspecialesRepository;
import com.hotel.room_service.infrastructure.mapper.ItemsEspecialesRowMapper;
import com.hotel.room_service.shared.util.AdapterErrorUtil;
import com.hotel.room_service.shared.util.SqlLoaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemsEspecialesAdapter implements ItemsEspecialesRepository {
    private final DatabaseClient databaseClient;
    private final ItemsEspecialesRowMapper itemsEspecialesRowMapper;
    private final String sqlMasPedidos = SqlLoaderUtil.load("querys/items_mas_pedidos.sql");
    private final String sqlMasRecientes = SqlLoaderUtil.load("querys/items_mas_recientes.sql");

    @Override
    public Mono<List<ItemEspecial>> consultarItemsMasPedidos(int cantidad) {
        return databaseClient.sql(sqlMasPedidos)
                .bind("CANTIDAD", cantidad)
                .map((row, metadata) -> itemsEspecialesRowMapper.mapRow(row))
                .all()
                .collectList()
                .onErrorResume(AdapterErrorUtil.mapError("Error al consultar items mas pedidos"));
    }

    @Override
    public Mono<List<ItemEspecial>> consultarItemsMasRecientes(int cantidad) {
        return databaseClient.sql(sqlMasRecientes)
                .bind("CANTIDAD", cantidad)
                .map((row, metadata) -> itemsEspecialesRowMapper.mapRow(row))
                .all()
                .collectList()
                .onErrorResume(AdapterErrorUtil.mapError("Error al consultar items mas resientes"));
    }
}
