package com.hotel.room_service.infrastructure.persistence;

import com.hotel.room_service.domain.model.Empleado;
import com.hotel.room_service.domain.port.EmpleadoRepository;
import com.hotel.room_service.shared.util.AdapterErrorUtil;
import com.hotel.room_service.shared.util.SqlLoaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class EmpleadoRepositoryAdapter implements EmpleadoRepository {

    private final DatabaseClient databaseClient;
    private final String sql = SqlLoaderUtil.load("querys/consultar-empleado.sql");

    @Override
    public Mono<Empleado> findByUsuario(String usuario) {
        return databaseClient.sql(sql)
                .bind("usuario", usuario)
                .map((row, metadata) -> Empleado.builder()
                        .emplId(row.get("empl_id", Short.class))
                        .emplUsuario(row.get("empl_usuario", String.class))
                        .emplPassword(row.get("empl_password", String.class))
                        .emplNombre(row.get("empl_nombre", String.class))
                        .emplRol(row.get("empl_rol", String.class))
                        .build()
                )
                .one()
                .onErrorResume(AdapterErrorUtil.mapError("Error al consultar empleado en BD"));
    }
}