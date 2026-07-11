package com.hotel.room_service.infrastructure.mapper;

import com.hotel.room_service.domain.model.ItemEspecial;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ItemsEspecialesRowMapper {

    public ItemEspecial mapRow(Row row) {
        return ItemEspecial.builder()
                .meitId(row.get( "meit_id", Short.class))
                .meitNombre(row.get( "meit_nombre", String.class))
                .meitImagenUrl(row.get( "meit_imagen_url", String.class))
                .meitPrecio(row.get("meit_precio", BigDecimal.class))
                .build();
    }
}
