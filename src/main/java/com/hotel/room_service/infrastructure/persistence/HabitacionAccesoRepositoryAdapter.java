package com.hotel.room_service.infrastructure.persistence;

import com.hotel.room_service.domain.exception.InternalServerErrorException;
import com.hotel.room_service.domain.model.HabitacionAcceso;
import com.hotel.room_service.domain.port.HabitacionAccesoRepository;
import com.hotel.room_service.infrastructure.mapper.HabitacionAccesoRowMapper;
import com.hotel.room_service.infrastructure.model.HabitacionAccesoRow;
import com.hotel.room_service.shared.util.SqlLoaderUtil;
import io.r2dbc.spi.R2dbcException;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class HabitacionAccesoRepositoryAdapter implements HabitacionAccesoRepository {

    private final DatabaseClient databaseClient;
    private final HabitacionAccesoRowMapper rowMapper;
    private final String sql = SqlLoaderUtil.load("querys/consultar-acceso.sql");

    @Override
    public Mono<HabitacionAcceso> findByHabitacionAndCodigo(String habitacion, String codigo) {
        return databaseClient.sql(sql)
                .bind("habitacion", habitacion)
                .bind("codigo", codigo)
                .map((row, metadata) -> rowMapper.mapRow(row))
                .one()
                .map(this::toDomain)
                .onErrorMap(R2dbcException.class, ex ->
                        new InternalServerErrorException("Error al consultar acceso", ex)
                );
    }

    private HabitacionAcceso toDomain(HabitacionAccesoRow row) {
        return HabitacionAcceso.builder()
                .haacId(row.getHaacId())
                .haacHabitacion(row.getHaacHabitacion())
                .haacCodigo(row.getHaacCodigo())
                .haacClienteNombre(row.getHaacClienteNombre())
                .haacClienteDoc(row.getHaacClienteDoc())
                .haacFechaInicio(row.getHaacFechaInicio())
                .haacFechaFin(row.getHaacFechaFin())
                .haacEstado(row.getHaacEstado())
                .build();
    }
}
