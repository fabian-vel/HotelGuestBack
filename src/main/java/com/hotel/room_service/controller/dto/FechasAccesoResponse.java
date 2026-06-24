package com.hotel.room_service.controller.dto;

import java.time.LocalDateTime;

public record FechasAccesoResponse(
        Long haacId,
        LocalDateTime haacFechaInicio,
        LocalDateTime haacFechaFin
) {
}
