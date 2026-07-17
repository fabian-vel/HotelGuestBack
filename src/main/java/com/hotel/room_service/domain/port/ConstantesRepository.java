package com.hotel.room_service.domain.port;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface ConstantesRepository {
    Mono<Map<String, String>> consultaConstantes(List<String> llaves);
}
