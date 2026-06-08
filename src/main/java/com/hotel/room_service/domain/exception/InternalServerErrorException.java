package com.hotel.room_service.domain.exception;

import org.springframework.http.HttpStatus;

/**
 * Error de conexión ca servidores (BD, red, SQL, etc.)
 * return error 500
 */
public class InternalServerErrorException extends AppException {
    public InternalServerErrorException(String userMessage, Throwable cause) {
        super(userMessage, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }
}
