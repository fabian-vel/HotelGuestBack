package com.hotel.room_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaPedido {
    private Short pediId;
    private String pediHabitacion;
    private BigDecimal pediTotal;
    private OffsetDateTime pediFechaCreacion;
    private String pediObservacion;
    private Short espeId;
    private String espeNombre;
    private List<PedidoDetalle> detallePedidoList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PedidoDetalle {
        private Short meitId;
        private String meitNombre;
        private int pedeCantidad;
        private BigDecimal pedeSubtotal;
        private Short mecaParentId;
        private Short mecaId;
        private String mecaNombre;
    }
}
