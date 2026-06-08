package com.hotel.room_service.infrastructure.mapper;

import com.hotel.room_service.infrastructure.model.MenuCategoriaRow;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MenuCategoriaRowMapper {
    public MenuCategoriaRow mapRow(Row row) {
        return MenuCategoriaRow.builder()
                .padreId(row.get("meca_id_padre", Short.class))
                .padreNombre(row.get("meca_nombre_padre", String.class))
                .padreDescripcion(row.get("meca_descripcion_padre", String.class))
                .padreImagenUrl(row.get("meca_imagen_url_padre", String.class))
                .hijoId(row.get("meca_id_hijo", Short.class))
                .hijoNombre(row.get("meca_nombre_hijo", String.class))
                .hijoDescripcion(row.get("meca_descripcion_hijo", String.class))
                .hijoImagenUrl(row.get("meca_imagen_url_hijo", String.class))
                .build();
    }
}

