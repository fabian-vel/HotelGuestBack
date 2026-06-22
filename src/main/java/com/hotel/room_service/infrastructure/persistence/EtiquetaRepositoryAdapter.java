package com.hotel.room_service.infrastructure.persistence;

import com.hotel.room_service.domain.exception.InternalServerErrorException;
import com.hotel.room_service.domain.model.Etiqueta;
import com.hotel.room_service.domain.model.EtiquetaRequest;
import com.hotel.room_service.domain.port.EtiquetaRepository;
import com.hotel.room_service.infrastructure.mapper.EtiquetaRowMapper;
import com.hotel.room_service.shared.util.SqlLoaderUtil;
import io.r2dbc.spi.R2dbcException;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EtiquetaRepositoryAdapter implements EtiquetaRepository {
    private final DatabaseClient databaseClient;
    private final EtiquetaRowMapper etiquetaRowMapper;
    private final String consultaPorItem = SqlLoaderUtil.load("querys/consulta-etiqueta-x-item.sql");
    private final String consultaPorCategoria = SqlLoaderUtil.load("querys/consulta-etiqueta-x-categoria.sql");

    @Override
    public Mono<List<Etiqueta>> consultaEtiqueta(EtiquetaRequest request) {
        String sql;
        String paramNombre;
        Short paramValor;

        if (request.isConsultaPorCategoria()) {
            sql = consultaPorCategoria;
            paramNombre = "MECA_ID";
            paramValor = request.getMecaId();
        } else {
            sql = consultaPorItem;
            paramNombre = "MEIT_ID";
            paramValor =  request.getMeitId();
        }

        return databaseClient.sql(sql)
                .bind(paramNombre, paramValor)
                .map((row, metadata) -> etiquetaRowMapper.mapRow(row))
                .all()
                .collectList()
                .onErrorMap(R2dbcException.class, ex ->
                        new InternalServerErrorException("Error al consultar etiquetas", ex)
                );

    }
}
