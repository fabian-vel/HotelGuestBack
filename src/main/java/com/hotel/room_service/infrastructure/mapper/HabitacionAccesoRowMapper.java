package com.hotel.room_service.infrastructure.mapper;

import com.hotel.room_service.infrastructure.model.HabitacionAccesoRow;
import io.r2dbc.spi.Row;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HabitacionAccesoRowMapper {
    public HabitacionAccesoRow mapRow(Row row) {
        return HabitacionAccesoRow.builder()
                .haacId(row.get("haac_id", Long.class))
                .haacHabitacion(row.get("haac_habitacion", String.class))
                .haacCodigo(row.get("haac_codigo", String.class))
                .haacClienteNombre(row.get("haac_cliente_nombre", String.class))
                .haacClienteDoc(row.get("haac_cliente_doc", String.class))
                .haacFechaInicio(row.get("haac_fecha_inicio", LocalDateTime.class))
                .haacFechaFin(row.get("haac_fecha_fin", LocalDateTime.class))
                .haacEstado(row.get("haac_estado", String.class))
                .build();
    }
}
