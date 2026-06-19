package com.hotel.room_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    private Short  meitId;
    private String meitNombre;
    private String meitDescripcion;
    private double meitPrecio;
    private String meitImagenUrl;
    private Short mecaId;
    private Short mecaParentId;
}
