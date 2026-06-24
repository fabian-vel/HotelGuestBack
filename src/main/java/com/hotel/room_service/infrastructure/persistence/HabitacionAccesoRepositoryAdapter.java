package com.hotel.room_service.infrastructure.persistence;

import com.hotel.room_service.domain.exception.InternalServerErrorException;
import com.hotel.room_service.domain.model.HabitacionAcceso;
import com.hotel.room_service.domain.model.HabitacionAccesoRequest;
import com.hotel.room_service.domain.port.HabitacionAccesoRepository;
import com.hotel.room_service.infrastructure.mapper.HabitacionAccesoRowMapper;
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
    private final String sqlAcceso = SqlLoaderUtil.load("querys/consultar-acceso.sql");
    private final String sqlFechaAcceso = SqlLoaderUtil.load("querys/consulta-fecha-acceso.sql");

    @Override
    public Mono<HabitacionAcceso> consultaHabitacionAcceso(String habitacion, String codigo) {
        return databaseClient.sql(sqlAcceso)
                .bind("habitacion", habitacion)
                .bind("codigo", codigo)
                .map((row, metadata) -> rowMapper.mapRow(row))
                .one()
                .onErrorMap(R2dbcException.class, ex ->
                        new InternalServerErrorException("Error al consultar acceso", ex)
                );
    }

    @Override
    public Mono<HabitacionAcceso> consultaFechasAcceso(HabitacionAccesoRequest request) {
        return databaseClient.sql(sqlFechaAcceso)
                .bind("HAAC_ID", request.getHaacId())
                .map((row, metadata) -> rowMapper.mapRow(row))
                .one()
                .onErrorMap(R2dbcException.class, ex ->
                        new InternalServerErrorException("Error al consultar fechas de acceso", ex)
                );
    }
}
