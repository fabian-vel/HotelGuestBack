package com.hotel.room_service.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoriaResponse {

    private Short  mecaId;
    private String mecaNombre;
    private String mecaDescripcion;
    private String mecaImagenUrl;
    private Short  mecaParentId;
}

