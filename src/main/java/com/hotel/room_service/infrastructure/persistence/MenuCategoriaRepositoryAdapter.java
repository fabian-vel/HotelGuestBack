package com.hotel.room_service.infrastructure.persistence;

import com.hotel.room_service.domain.exception.InternalServerErrorException;
import com.hotel.room_service.domain.port.MenuCategoriaRepository;
import com.hotel.room_service.infrastructure.mapper.MenuCategoriaRowMapper;
import com.hotel.room_service.infrastructure.model.MenuCategoriaRow;
import com.hotel.room_service.shared.util.SqlLoaderUtil;
import io.r2dbc.spi.R2dbcException;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MenuCategoriaRepositoryAdapter implements MenuCategoriaRepository {

    private final DatabaseClient databaseClient;
    private final MenuCategoriaRowMapper menuCategoriaRowMapper;
    private final String sql = SqlLoaderUtil.load("querys/consultar-categoria.sql");

    @Override
    public Mono<List<MenuCategoriaRow>> consultarCategorias() {
        return databaseClient.sql(sql)
                .map((row, metadata) -> menuCategoriaRowMapper.mapRow(row))
                .all()
                .collectList()
                .onErrorMap(R2dbcException.class, ex ->
                        new InternalServerErrorException("Error al consultar categorías en BD", ex)
                );
    }
}
