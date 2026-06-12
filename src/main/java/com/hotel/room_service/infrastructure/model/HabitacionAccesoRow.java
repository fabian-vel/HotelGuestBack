package com.hotel.room_service.infrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionAccesoRow {
    private Long haacId;
    private String haacHabitacion;
    private String haacCodigo;
    private String haacClienteNombre;
    private String haacClienteDoc;
    private LocalDateTime haacFechaInicio;
    private LocalDateTime haacFechaFin;
    private String haacEstado;
}
