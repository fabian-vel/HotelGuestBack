package com.hotel.room_service.domain.port;

import com.hotel.room_service.domain.model.HabitacionAcceso;
import com.hotel.room_service.domain.model.HabitacionAccesoRequest;
import reactor.core.publisher.Mono;

public interface HabitacionAccesoRepository {
    Mono<HabitacionAcceso> consultaHabitacionAcceso(String habitacion, String codigo);

    Mono<HabitacionAcceso> consultaFechasAcceso(HabitacionAccesoRequest request);
}
