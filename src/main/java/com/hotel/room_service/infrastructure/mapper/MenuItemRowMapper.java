package com.hotel.room_service.infrastructure.mapper;

import com.hotel.room_service.domain.model.MenuItem;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuItemRowMapper {
    public MenuItem mapRow(Row row) {
        Double precio = row.get("meit_precio", Double.class);

        return MenuItem.builder()
                .meitId(row.get("meit_id", Short.class))
                .meitNombre(row.get("meit_nombre", String.class))
                .meitDescripcion(row.get("meit_descripcion", String.class))
                .meitPrecio(precio != null ? precio : 0d)
                .meitImagenUrl(row.get("meit_imagen_url", String.class))
                .mecaId(row.get("meca_id", Short.class))
                .mecaParentId(row.get("meca_parent_id", Short.class))
                .build();
    }
}
