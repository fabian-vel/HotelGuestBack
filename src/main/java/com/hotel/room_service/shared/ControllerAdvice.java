package com.hotel.room_service.shared;

import com.hotel.room_service.domain.exception.AppException;
import com.hotel.room_service.domain.exception.SqlLoadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    /**
     * Cubre InternalServerErrorException, NotFoundException y BusinessException
     */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleApp(AppException ex) {
        log.error("[{}] {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(new ErrorResponse(ex.getUserMessage()));
    }

    @ExceptionHandler(SqlLoadException.class)
    public ResponseEntity<ErrorResponse> handleSqlLoad(SqlLoadException ex) {
        log.error("[SqlLoadException] {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error de configuración interna"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("[Exception] Error inesperado: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error inesperado"));
    }

    public record ErrorResponse(String message) {
    }
}
