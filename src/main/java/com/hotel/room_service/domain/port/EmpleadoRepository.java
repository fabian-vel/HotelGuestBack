package com.hotel.room_service.domain.port;

import com.hotel.room_service.domain.model.Empleado;
import reactor.core.publisher.Mono;

public interface EmpleadoRepository {
    Mono<Empleado> findByUsuario(String usuario);
}
