package com.hotel.room_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedido {
    private Long pediId;
    private Short meitId;
    private Short cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private String observacion;
}
