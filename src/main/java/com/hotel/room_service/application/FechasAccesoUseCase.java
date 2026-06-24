package com.hotel.room_service.application;

import com.hotel.room_service.domain.model.HabitacionAcceso;
import com.hotel.room_service.domain.model.HabitacionAccesoRequest;
import com.hotel.room_service.domain.port.HabitacionAccesoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FechasAccesoUseCase {
    private final HabitacionAccesoRepository habitacionAccesoRepository;

    public Mono<HabitacionAcceso> consultaFechaAcceso(HabitacionAccesoRequest request) {
        if (request.getHaacId() == null) {
            return Mono.error(new IllegalArgumentException("El haacId no puede ser nulo"));
        }

        return habitacionAccesoRepository.consultaFechasAcceso(request);
    }
}
