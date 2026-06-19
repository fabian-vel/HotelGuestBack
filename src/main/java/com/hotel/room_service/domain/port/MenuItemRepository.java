package com.hotel.room_service.domain.port;

import com.hotel.room_service.domain.model.MenuItem;
import com.hotel.room_service.domain.model.MenuItemRequest;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MenuItemRepository {
    Mono<List<MenuItem>> consultaMenuItemsPorCategoria(MenuItemRequest request);
}
