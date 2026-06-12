package com.hotel.room_service.shared.constant;

import lombok.Getter;

@Getter
public enum SuccessMessages {
    CATEGORIAS_CONSULTADAS("Categorías consultadas exitosamente"),
    LOGIN_EXITOSO("Sesión iniciada exitosamente");

    private final String message;

    SuccessMessages(String message) { this.message = message; }
}
