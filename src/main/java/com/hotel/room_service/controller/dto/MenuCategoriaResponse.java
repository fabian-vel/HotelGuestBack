package com.hotel.room_service.controller.dto;

import java.util.List;

public record MenuCategoriaResponse(
        Short mecaId,
        String mecaNombre,
        String mecaDescripcion,
        String mecaImagenUrl,
        Short mecaParentId,
        List<MenuCategoriaResponse> subCategorias
) {
}

