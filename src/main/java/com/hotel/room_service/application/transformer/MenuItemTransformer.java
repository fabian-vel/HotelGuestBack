package com.hotel.room_service.application.transformer;

import com.hotel.room_service.controller.dto.MenuItemResponse;
import com.hotel.room_service.domain.model.MenuItem;

public class MenuItemTransformer {
    public static MenuItemResponse toResponse(MenuItem domain) {
        return new MenuItemResponse(
                domain.getMeitId(),
                domain.getMeitNombre(),
                domain.getMeitDescripcion(),
                domain.getMeitPrecio(),
                domain.getMeitImagenUrl(),
                domain.getMecaId(),
                domain.getMecaParentId()
        );
    }
}
