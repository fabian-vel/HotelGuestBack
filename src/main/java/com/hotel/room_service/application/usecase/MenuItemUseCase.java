package com.hotel.room_service.application.usecase;

import com.hotel.room_service.domain.exception.BusinessException;
import com.hotel.room_service.domain.model.MenuItem;
import com.hotel.room_service.domain.model.MenuItemRequest;
import com.hotel.room_service.domain.port.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemUseCase {

    private final MenuItemRepository menuItemRepository;

    public Mono<List<MenuItem>> consultarItemsPorCategoria(MenuItemRequest request) {
        if (request.getMecaId() == null) {
            return Mono.error(new BusinessException("El mecaId no pueden ser nulos"));
        }

        return menuItemRepository.consultaMenuItemsPorCategoria(request);
    }
}
