package com.hotel.room_service.controller.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record ConsultaPedidoResponse(
        Short pediId,
        String pediHabitacion,
        BigDecimal pediTotal,
        OffsetDateTime pediFechaCreacion,
        String pediObservacion,
        Short espeId,
        String espeNombre,
        List<PedidoDetalle> detallePedidoList
) {
    public record PedidoDetalle(
            Short meitId,
            String meitNombre,
            int pedeCantidad,
            BigDecimal pedeSubtotal
    ) {}
}