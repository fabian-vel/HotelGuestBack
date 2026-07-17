package com.hotel.room_service.infrastructure.persistence;

import com.hotel.room_service.domain.port.ConstantesRepository;
import com.hotel.room_service.shared.util.AdapterErrorUtil;
import com.hotel.room_service.shared.util.SqlLoaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ConstantesAdapter implements ConstantesRepository {
    private final DatabaseClient databaseClient;
    private final String sql = SqlLoaderUtil.load("querys/consulta-constantes.sql");

    @Override
    public Mono<Map<String, String>> consultaConstantes(List<String> llaves) {
        return databaseClient.sql(sql)
                .bind("LLAVES", llaves.toArray(new String[0]))
                .map((row, metadata) -> Map.entry(
                        Objects.requireNonNull(row.get("cons_llave", String.class)),
                        Objects.requireNonNull(row.get("cons_valor", String.class))
                ))
                .all()
                .collectMap(Map.Entry::getKey, Map.Entry::getValue)
                .onErrorResume(AdapterErrorUtil.mapError("Error al consultar constantes"));
    }
}
