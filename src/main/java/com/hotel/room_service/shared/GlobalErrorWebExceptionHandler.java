package com.hotel.room_service.shared;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.room_service.domain.exception.AppException;
import com.hotel.room_service.domain.exception.BusinessException;
import com.hotel.room_service.domain.exception.InternalServerErrorException;
import com.hotel.room_service.domain.exception.NotFoundException;
import com.hotel.room_service.domain.exception.SqlLoadException;
import com.hotel.room_service.shared.constant.ErrorMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.UUID;

@Component
@Order(-2)
@RequiredArgsConstructor
@Slf4j
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, @NonNull Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        ErrorDescriptor descriptor = resolve(ex);
        String correlationId = UUID.randomUUID().toString(); // ← nuevo

        log.error("[{}][{}] {} - path={}",
                correlationId,                               // ← trazabilidad
                descriptor.code(),
                ex.getMessage(),
                exchange.getRequest().getPath().value(),
                ex
        );

        ErrorResponse payload = new ErrorResponse(
                OffsetDateTime.now().toString(),
                descriptor.status().value(),
                descriptor.code(),
                descriptor.message(),
                exchange.getRequest().getPath().value(),
                correlationId                                // ← el front lo muestra al usuario
        );

        exchange.getResponse().setStatusCode(descriptor.status());
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return exchange.getResponse().writeWith(Mono.just(
                exchange.getResponse().bufferFactory().wrap(toJson(payload))
        ));
    }

    private ErrorDescriptor resolve(Throwable ex) {
        return switch (ex) {                                 // ← switch pattern (Java 21)
            case NotFoundException e -> new ErrorDescriptor(HttpStatus.NOT_FOUND, "NOT_FOUND", e.getUserMessage());
            case BusinessException e ->
                    new ErrorDescriptor(HttpStatus.UNPROCESSABLE_ENTITY, "BUSINESS_ERROR", e.getUserMessage());
            case InternalServerErrorException e ->
                    new ErrorDescriptor(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", e.getUserMessage());
            case SqlLoadException e ->
                    new ErrorDescriptor(HttpStatus.INTERNAL_SERVER_ERROR, "CONFIG_ERROR", ErrorMessages.CONFIG_ERROR.getMessage());
            case AppException e -> new ErrorDescriptor(e.getHttpStatus(), "APP_ERROR", e.getUserMessage());
            default ->
                    new ErrorDescriptor(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", ErrorMessages.INTERNAL_ERROR.getMessage());
        };
    }

    private byte[] toJson(Object payload) {
        try {
            return objectMapper.writeValueAsBytes(payload);
        } catch (JsonProcessingException e) {
            return "{\"code\":\"INTERNAL_ERROR\",\"message\":\"Error interno del servidor\"}".getBytes(StandardCharsets.UTF_8);
        }
    }

    private record ErrorDescriptor(HttpStatus status, String code, String message) {
    }

    private record ErrorResponse(
            String timestamp,
            int status,
            String code,
            String message,
            String path,
            String correlationId               // ← nuevo
    ) {
    }
}