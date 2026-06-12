package com.hotel.room_service.domain.port;

import com.hotel.room_service.domain.model.HabitacionAcceso;
import reactor.core.publisher.Mono;

public interface HabitacionAccesoRepository {
    Mono<HabitacionAcceso> findByHabitacionAndCodigo(String habitacion, String codigo);

}
