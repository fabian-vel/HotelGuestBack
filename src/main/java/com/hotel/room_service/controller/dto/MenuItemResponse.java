package com.hotel.room_service.controller.dto;

public record MenuItemResponse(
        Short meitId,
        String meitNombre,
        String meitDescripcion,
        double meitPrecio,
        String meitImagenUrl,
        Short mecaId,
        Short mecaParentId
) {
}
