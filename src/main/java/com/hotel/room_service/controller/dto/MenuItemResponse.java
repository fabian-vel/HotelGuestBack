package com.hotel.room_service.controller.dto;

import java.util.List;

public record MenuItemResponse(
        Short meitId,
        String meitNombre,
        String meitDescripcion,
        double meitPrecio,
        String meitImagenUrl,
        Short mecaId,
        Short mecaParentId,
        List<EtiquetaResponse> etiquetas
) {
}
