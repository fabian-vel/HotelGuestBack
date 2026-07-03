package com.hotel.room_service.infrastructure.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.room_service.domain.model.ConsultaPedido;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ConsultaPedidoRowMapper {
    private final ObjectMapper objectMapper;

    public ConsultaPedido mapRow(Row row) {
        return ConsultaPedido.builder()
                .pediId(row.get("pedi_id", Short.class))
                .pediHabitacion(row.get("pedi_habitacion", String.class))
                .pediTotal(row.get("pedi_total", BigDecimal.class))
                .pediFechaCreacion(row.get("pedi_fecha_creacion", LocalDateTime.class))
                .pediObservacion(row.get("pedi_observacion", String.class))
                .espeId(row.get("espe_id", Short.class))
                .espeNombre(row.get("espe_nombre", String.class))
                .detallePedidoList(parseDetalle(row.get("pedido_detalle", String.class)))
                .build();
    }

    private List<ConsultaPedido.PedidoDetalle> parseDetalle(String json) {
        if (json == null) return List.of();
        try {
            return objectMapper.readValue(json,
                    new TypeReference<List<ConsultaPedido.PedidoDetalle>>() {});
        } catch (Exception e) {
            return List.of();
        }
    }
}
