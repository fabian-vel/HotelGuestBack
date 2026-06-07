package com.hotel.room_service.domain.exception;

public class SqlLoadException extends RuntimeException {
    public SqlLoadException(String path, Throwable cause) {
        super("Error cargando SQL: " + path, cause);
    }
}
