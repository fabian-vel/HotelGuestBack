package com.hotel.room_service.infrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoriaRow {
    private Short  padreId;
    private String padreNombre;
    private String padreDescripcion;
    private String padreImagenUrl;
    private Short  hijoId;
    private String hijoNombre;
    private String hijoDescripcion;
    private String hijoImagenUrl;
}
