package com.hotel.room_service.infrastructure.persistence;

import com.hotel.room_service.domain.model.MenuItem;
import com.hotel.room_service.domain.model.MenuItemRequest;
import com.hotel.room_service.domain.port.MenuItemRepository;
import com.hotel.room_service.infrastructure.mapper.MenuItemRowMapper;
import com.hotel.room_service.shared.util.AdapterErrorUtil;
import com.hotel.room_service.shared.util.SqlLoaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MenuItemRepositoryAdapter implements MenuItemRepository {
    private final DatabaseClient databaseClient;
    private final MenuItemRowMapper menuItemRowMapper;
    private final String sql = SqlLoaderUtil.load("querys/consultar-menu-items.sql");

    @Override
    public Mono<List<MenuItem>> consultaMenuItemsPorCategoria(MenuItemRequest request) {
        return databaseClient.sql(sql)
                .bind("MECA_ID", request.getMecaId())
                .map((row, metadata) -> menuItemRowMapper.mapRow(row))
                .all()
                .collectList()
                .onErrorResume(AdapterErrorUtil.mapError("Error al consultar menu en BD"));
    }
}
