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
public class DatosPedido {
    private String haacHabitacion;
    private String haacClienteNombre;
    private Short meitId;
    private BigDecimal meitPrecio;
    private Short estadoPedidoId;
}
