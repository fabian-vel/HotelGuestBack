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
public class ItemEspecial {
    private Short meitId;
    private String meitNombre;
    private String meitImagenUrl;
    private BigDecimal meitPrecio;
}
