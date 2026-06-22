package com.hotel.room_service.controller.dto;

public record EtiquetaResponse(
        Short etiqId,
        String etiqNombre,
        String etiqDescripcion
) {
}
