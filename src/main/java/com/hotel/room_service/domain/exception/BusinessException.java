package com.hotel.room_service.domain.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción para regla de negocio violada
 * return error 422
 */
public class BusinessException extends AppException {
    public BusinessException(String userMessage) {
        super(userMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}