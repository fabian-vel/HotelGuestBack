package com.hotel.pedidos.infrastructure.persistence;

import com.hotel.pedidos.domain.model.MenuCategoria;
import com.hotel.pedidos.domain.port.MenuCategoriaRepository;
import com.hotel.pedidos.infrastructure.mapper.MenuCategoriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
public class MenuCategoriaJdbcAdapter implements MenuCategoriaRepository {

    private final JdbcTemplate jdbcTemplate;
    private final MenuCategoriaMapper mapper;

    private static final String SQL = """
            SELECT
                meca_id,
                meca_nombre,
                meca_descripcion,
                meca_imagen_url,
                meca_parent_id
            FROM mst_menu_categorias
            WHERE meca_estado            = 'A'
              AND meca_fecha_eliminacion IS NULL
              AND (
                    (CAST(? AS VARCHAR) IS NULL AND meca_parent_id IS NULL)
                    OR
                    (CAST(? AS VARCHAR) IS NOT NULL AND meca_parent_id = ANY(string_to_array(?, ',')::SMALLINT[]))
              )
            ORDER BY meca_nombre
            """;

    @Override
    public Flux<MenuCategoria> consultarCategorias(String parentIds) {
        return Mono.fromCallable(() ->
                        jdbcTemplate.query(
                                SQL,
                                (rs, rowNum) -> mapper.toDomain(
                                        rs.getShort("meca_id"),
                                        rs.getString("meca_nombre"),
                                        rs.getString("meca_descripcion"),
                                        rs.getString("meca_imagen_url"),
                                        rs.getObject("meca_parent_id", Short.class)
                                ),
                                parentIds, parentIds, parentIds
                        )
                )
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable);
    }
}

