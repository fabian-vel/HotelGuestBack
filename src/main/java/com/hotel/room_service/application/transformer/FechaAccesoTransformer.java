package com.hotel.room_service.application.transformer;

import com.hotel.room_service.controller.dto.FechasAccesoResponse;
import com.hotel.room_service.domain.model.HabitacionAcceso;

public class FechaAccesoTransformer {
    public static FechasAccesoResponse toResponse(HabitacionAcceso domain) {
        return new FechasAccesoResponse(
                domain.getHaacId(),
                domain.getHaacFechaInicio(),
                domain.getHaacFechaFin()
        );
    }
}
