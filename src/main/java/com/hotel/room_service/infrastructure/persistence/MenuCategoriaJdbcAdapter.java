package com.hotel.room_service.infrastructure.persistence;

import com.hotel.room_service.domain.model.MenuCategoria;
import com.hotel.room_service.domain.port.MenuCategoriaRepository;
import com.hotel.room_service.infrastructure.mapper.MenuCategoriaRowMapper;
import com.hotel.room_service.shared.util.SqlLoaderUtil;
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
    private final MenuCategoriaRowMapper rowMapper;
    private final String sql = SqlLoaderUtil.load("querys/consultar-categoria.sql");

    @Override
    public Flux<MenuCategoria> consultarCategorias(String parentIds) {
        return Mono.fromCallable(() ->
                        jdbcTemplate.query(sql, rowMapper, parentIds, parentIds, parentIds)
                )
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable);
    }
}
