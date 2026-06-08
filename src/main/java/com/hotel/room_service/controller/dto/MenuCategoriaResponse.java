package com.hotel.room_service.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoriaResponse {

    private Short  mecaId;
    private String mecaNombre;
    private String mecaDescripcion;
    private String mecaImagenUrl;
    private List<MenuCategoriaResponse> subCategorias;
}

