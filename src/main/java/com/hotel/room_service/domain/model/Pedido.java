package com.hotel.room_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    private Short haacId;
    private String habitacion;
    private String clienteNombre;
    private Short estadoPedidoId;
    private String observacion;
    private BigDecimal total;
}
