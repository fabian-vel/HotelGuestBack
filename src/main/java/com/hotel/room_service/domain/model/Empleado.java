package com.hotel.room_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    private Short emplId;
    private String emplUsuario;
    private String emplPassword;
    private String emplNombre;
    private String emplRol;
}
