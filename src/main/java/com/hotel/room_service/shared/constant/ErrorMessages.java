package com.hotel.room_service.shared.constant;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    CONFIG_ERROR("Error de configuración interna"),
    INTERNAL_ERROR("Error interno del servidor");

    private final String message;

    ErrorMessages(String message) { this.message = message; }
}