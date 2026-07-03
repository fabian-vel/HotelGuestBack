package com.hotel.room_service.application.rule;

import reactor.core.publisher.Mono;

public interface ReglaNegocio<T> {
    String getRuleId();
    Mono<Void> validar(T contexto);
}
