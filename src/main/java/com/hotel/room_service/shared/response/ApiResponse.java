package com.hotel.room_service.shared.response;

import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

public record ApiResponse<T>(
        String timestamp,
        int status,
        String message,
        T data,
        int size
) {
    public static <T> ApiResponse<T> ok(String message, T data, int size) {
        return new ApiResponse<>(
                OffsetDateTime.now().toString(),
                HttpStatus.OK.value(),
                message,
                data,
                size
        );
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(
                OffsetDateTime.now().toString(),
                HttpStatus.OK.value(),
                message,
                data,
                0
        );
    }
}
