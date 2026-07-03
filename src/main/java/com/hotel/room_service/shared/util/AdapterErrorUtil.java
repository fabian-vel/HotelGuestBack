package com.hotel.room_service.shared.util;

import com.hotel.room_service.domain.exception.InternalServerErrorException;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class AdapterErrorUtil {
    private AdapterErrorUtil() {}

    public static <T> Function<Throwable, Mono<T>> mapError(String mensaje) {
        return ex -> {
            if (ex instanceof InternalServerErrorException) return Mono.error(ex);
            return Mono.error(new InternalServerErrorException(mensaje, ex));
        };
    }
}
