package com.hotel.pedidos.infrastructure.mapper;

import com.hotel.pedidos.controller.dto.MenuCategoriaResponse;
import com.hotel.pedidos.domain.model.MenuCategoria;
import org.springframework.stereotype.Component;

@Component
public class MenuCategoriaMapper {
    public MenuCategoriaResponse toResponse(MenuCategoria domain) {
        return MenuCategoriaResponse.builder()
                .mecaId(domain.getMecaId())
                .mecaNombre(domain.getMecaNombre())
                .mecaDescripcion(domain.getMecaDescripcion())
                .mecaImagenUrl(domain.getMecaImagenUrl())
                .mecaParentId(domain.getMecaParentId())
                .build();
    }

    public MenuCategoria toDomain(Short mecaId,
                                  String mecaNombre,
                                  String mecaDescripcion,
                                  String mecaImagenUrl,
                                  Short  mecaParentId) {
        return MenuCategoria.builder()
                .mecaId(mecaId)
                .mecaNombre(mecaNombre)
                .mecaDescripcion(mecaDescripcion)
                .mecaImagenUrl(mecaImagenUrl)
                .mecaParentId(mecaParentId)
                .build();
    }
}

