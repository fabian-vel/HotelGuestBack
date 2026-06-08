package com.hotel.room_service.domain.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción para recurso no encontrado
 * return error 404
 */
public class NotFoundException extends AppException {
    public NotFoundException(String userMessage) {
        super(userMessage, HttpStatus.NOT_FOUND);
    }
}